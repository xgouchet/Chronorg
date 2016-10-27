package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.data.repositories.EventRepository;
import fr.xgouchet.chronorg.ui.contracts.EventEditContract;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Xavier Gouchet
 */
@Trace
public class EventEditPresenter implements EventEditContract.Presenter {

    @Nullable private Event event;

    @Nullable /*package*/ EventEditContract.View view;

    @NonNull private final EventRepository eventRepository;

    @NonNull private String name = "";
    @NonNull private ReadableInstant instant = new DateTime("1970-01-01T00:00:00Z");
    @ColorInt private int color;

    public EventEditPresenter(@NonNull EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    public void setEvent(@NonNull Event event) {
        this.event = event;
        name = event.getName();
        instant = event.getInstant();
        color = event.getColor();
    }

    @Override public void setView(@NonNull EventEditContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void subscribe() {
        if (view == null) return;

        view.setContent(name, instant, color);
    }

    @Override public void unsubscribe() {

    }

    @Override public void load(boolean force) {
        // empty
    }

    @Override public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override public void setInstant(@NonNull String dateTimeIso8601) {
        instant = new DateTime(dateTimeIso8601);
    }

    @Override public void setColor(@ColorInt int color) {
        this.color = color;
    }

    @Override
    public void saveEvent(@NonNull String inputNameText) {
        if (view == null) return;
        if (event == null) return;

        event.setName(inputNameText);
        event.setInstant(instant);
        event.setColor(color);
        eventRepository.save(event)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override public void onCompleted() {
                        view.eventSaved();
                    }

                    @Override public void onError(Throwable e) {
                        view.eventSaveError(e);
                    }

                    @Override public void onNext(Void nothing) {
                        // Ignore
                    }
                });
    }
}
