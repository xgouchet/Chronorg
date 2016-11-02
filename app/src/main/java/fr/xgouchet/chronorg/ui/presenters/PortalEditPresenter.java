package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import org.joda.time.DateTime;
import org.joda.time.Interval;
import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.formatters.Formatter;
import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.data.repositories.PortalRepository;
import fr.xgouchet.chronorg.ui.contracts.PortalEditContract;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Xavier Gouchet
 */
@Trace
public class PortalEditPresenter implements PortalEditContract.Presenter {

    @Nullable private Portal portal;

    @Nullable /*package*/ PortalEditContract.View view;

    @NonNull private final PortalRepository portalRepository;

    @NonNull private String name = "";
    @NonNull private Interval delay = new Interval(new DateTime(), new DateTime());
    @ColorInt private int color;
    @Portal.Direction private int direction;
    @NonNull private final Formatter<ReadableInstant> formatter;

    public PortalEditPresenter(@NonNull PortalRepository portalRepository,
                               @NonNull Formatter<ReadableInstant> formatter) {
        this.portalRepository = portalRepository;
        this.formatter = formatter;
    }

    @Override public Formatter<ReadableInstant> getReadableInstantFormatter() {
        return formatter;
    }

    public void setPortal(@NonNull Portal portal) {
        this.portal = portal;
        name = portal.getName();
        color = portal.getColor();
        delay = portal.getDelay();
        direction = portal.getDirection();
    }

    @Override public void setView(@NonNull PortalEditContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void subscribe() {
        if (view == null) return;

        view.setContent(name, delay, direction, color);
    }

    @Override public void unsubscribe() {

    }

    @Override public void load(boolean force) {
        // empty
    }

    @Override public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override public void setDelayStart(@NonNull String delayStart) {
        ReadableInstant start = new DateTime(delayStart);
        this.delay = new Interval(start, delay.getEnd());
    }

    @Override public void setDelayEnd(@NonNull String delayEnd) {
        ReadableInstant end = new DateTime(delayEnd);
        this.delay = new Interval(delay.getStart(), end);
    }

    @Override public void setColor(int color) {
        this.color = color;
    }

    @Override  public void setDirection(@Portal.Direction int direction) {
        this.direction = direction;
    }

    @Override public void savePortal(@NonNull String inputNameText) {
        if (view == null) return;
        if (portal == null) return;

        portal.setName(inputNameText);
        portal.setDelay(delay);
        portal.setDirection(direction);
        portal.setColor(color);
        portalRepository.save(portal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override public void onCompleted() {
                        view.portalSaved();
                    }

                    @Override public void onError(Throwable e) {
                        view.portalSaveError(e);
                    }

                    @Override public void onNext(Void nothing) {
                        // Ignore
                    }
                });
    }
}
