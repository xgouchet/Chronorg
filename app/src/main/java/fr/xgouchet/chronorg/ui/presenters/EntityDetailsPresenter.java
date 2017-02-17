package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.ui.contracts.EntityDetailsContract;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Xavier Gouchet
 */
@Trace
public class EntityDetailsPresenter implements EntityDetailsContract.Presenter {

    @Nullable /*package*/ Entity entity;
    @Nullable /*package*/ EntityDetailsContract.View view;
    @NonNull private final EntityRepository entityRepository;

    @NonNull private final CompositeSubscription subscriptions;

    public EntityDetailsPresenter(@NonNull EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
        subscriptions = new CompositeSubscription();

    }

    public void setEntity(@Nullable Entity entity) {
        this.entity = entity;
    }

    public void setView(@NonNull EntityDetailsContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void subscribe() {
        load(true);
    }

    @Override public void unsubscribe() {
        subscriptions.unsubscribe();
    }

    @Override public void load(boolean force) {
        if (view == null) return;
        if (entity == null) return;

        if (!force) {
            view.setContent(entity);
            return;
        }

        Subscription subscription = entityRepository
                .get(entity.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Entity>() {
                    @Override public void onCompleted() {
                        view.setContent(entity);
                    }

                    @Override public void onError(Throwable e) {
                        view.setError(e);
                    }

                    @Override public void onNext(Entity entity) {
                        onEntityLoaded(entity);
                    }
                });

        subscriptions.add(subscription);
    }

    /*package*/ void onEntityLoaded(Entity entity) {
        if (view == null) return;
        if (entity != null) {
            this.entity = entity;
            view.setContent(entity);
        }
    }

    @Override public void editEntity() {
        if (view == null) return;
        if (entity == null) return;
        view.showEditEntityUi(entity);
    }

    @Override public void deleteEntity() {
        if (view == null) return;
        if (entity == null) return;
        Subscription subscription = entityRepository
                .delete(entity)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Void>() {
                    @Override public void onCompleted() {
                        view.entityDeleted();
                    }

                    @Override public void onError(Throwable e) {
                        view.entityDeleteError(e);
                    }

                    @Override public void onNext(Void project) {
                    }
                });

        subscriptions.add(subscription);
    }
}
