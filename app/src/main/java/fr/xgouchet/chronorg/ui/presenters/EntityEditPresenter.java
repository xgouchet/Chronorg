package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.ui.contracts.EntityEditContract;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Xavier Gouchet
 */
@Trace
public class EntityEditPresenter implements EntityEditContract.Presenter {

    @Nullable private Entity entity;

    @Nullable /*package*/ EntityEditContract.View view;

    @NonNull private final EntityRepository entityRepository;

    @NonNull private String name = "‽";
    @Nullable private String description;
    @NonNull private ReadableInstant birth = new DateTime("1970-01-01T00:00:00Z");
    @NonNull private ReadableInstant death = new DateTime("2038-01-23T00:00:00Z");
    @ColorInt private int color;

    public EntityEditPresenter(@NonNull EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public void setEntity(@NonNull Entity entity) {
        this.entity = entity;
        name = entity.getName();
        birth = entity.getBirth();
        death = entity.getDeath();
        color = entity.getColor();
    }

    @Override public void setView(@NonNull EntityEditContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void subscribe() {
        if (view == null) return;

        view.setContent(name, description, birth, death, color);
    }

    @Override public void unsubscribe() {

    }

    @Override public void load(boolean force) {
        // empty
    }

    @Override public void setName(@NonNull String name) {
        this.name = name;
    }

    @Override public void setDescription(@Nullable String description) {
        this.description = description;
    }

    @Override public void setBirth(@NonNull String dateTimeIso8601) {
        birth = new DateTime(dateTimeIso8601);
    }

    @Override public void setDeath(@NonNull String dateTimeIso8601) {
        death = new DateTime(dateTimeIso8601);
    }

    @Override public void setColor(@ColorInt int color) {
        this.color = color;
    }

    @Override
    public void saveEntity(@NonNull String inputNameText) {
        if (view == null) return;
        if (entity == null) return;

        entity.setName(inputNameText);
        entity.setBirth(birth);
        entity.setDeath(death);
        entity.setColor(color);
        entityRepository.save(entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override public void onCompleted() {
                        view.entitySaved();
                    }

                    @Override public void onError(Throwable e) {
                        view.entitySaveError(e);
                    }

                    @Override public void onNext(Void nothing) {
                        // Ignore
                    }
                });
    }
}
