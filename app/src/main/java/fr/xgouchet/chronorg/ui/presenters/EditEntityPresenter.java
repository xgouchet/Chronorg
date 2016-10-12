package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.ui.contracts.EditEntityContract;
import fr.xgouchet.chronorg.ui.contracts.EditProjectContract;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Xavier Gouchet
 */
public class EditEntityPresenter implements EditEntityContract.Presenter {

    @NonNull private final Entity entity;

    @NonNull /*package*/ final EditEntityContract.View view;

    @NonNull private final EntityRepository entityRepository;

    private final int projectId;

    @NonNull private String name;
    @Nullable private String description;
    @NonNull private ReadableInstant birth;
    @Nullable private ReadableInstant death;
    @ColorInt private int colour;

    public EditEntityPresenter(@NonNull EntityRepository entityRepository,
                               @NonNull EditEntityContract.View view,
                               @NonNull Entity entity) {
        this.entityRepository = entityRepository;
        this.view = view;
        this.entity = entity;

        projectId = entity.getProjectId();
        name = entity.getName();
        description = entity.getDescription();
        birth = entity.getBirth();
        death = entity.getDeath();
        colour = entity.getColour();

        view.setPresenter(this);
    }

    @Override public void subscribe() {
        view.setContent(name, description, birth, death, colour);
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

    @Override public void setColour(@ColorInt int colour) {
        this.colour = colour;
    }

    @Override
    public void saveEntity(@NonNull String inputNameText, @NonNull String inputDescText) {
        // check input
        if (TextUtils.isEmpty(inputNameText)) {
            view.invalidName(EditProjectContract.EMPTY);
            return;
        }

        entity.setName(inputNameText);
        entity.setDescription(inputDescText);
        entity.setBirth(birth);
        entity.setDeath(death);
        entity.setColour(colour);
        entityRepository.saveEntity(entity)
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
