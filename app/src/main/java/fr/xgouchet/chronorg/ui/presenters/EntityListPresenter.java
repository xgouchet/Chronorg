package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.deezer.android.counsel.annotations.Trace;

import java.util.ArrayList;
import java.util.List;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.ui.contracts.EntityListContract;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Xavier Gouchet
 */
@Trace
public class EntityListPresenter implements EntityListContract.Presenter {

    @NonNull private final List<Entity> entities;
    @NonNull private final EntityRepository entityRepository;
    @NonNull private final CompositeSubscription subscriptions;

    @Nullable /*package*/ EntityListContract.View view;
    @Nullable private Project project;

    public EntityListPresenter(@NonNull EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
        entities = new ArrayList<>();
        subscriptions = new CompositeSubscription();
    }

    public void setProject(@NonNull Project project) {
        this.project = project;
    }

    public void setView(@NonNull EntityListContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }


    @Override public void load(boolean force) {
        if (view == null) return;
        if (project == null) return;

        if (!force) {
            view.setContent(entities);
        }

        view.setLoading(true);

        entities.clear();

        Subscription subscription = entityRepository
                .getEntitiesInProject(project.getId())
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Entity>>() {
                    @Override public void onCompleted() {
                        view.setLoading(false);
                    }

                    @Override public void onError(Throwable e) {
                        view.setLoading(false);
                        view.setError(e);
                    }

                    @Override public void onNext(List<Entity> entities) {
                        onEntitiesLoaded(entities);
                    }
                });

        subscriptions.add(subscription);
    }

    /*package*/ void onEntitiesLoaded(List<Entity> entities) {
        this.entities.addAll(entities);
        if (view == null) return;

        if (this.entities.isEmpty()) {
            view.setEmpty();
        } else {
            view.setContent(this.entities);
        }
    }

    @Override public void itemSelected(@NonNull Entity entity) {
        if (view == null) return;
        view.showItem(entity);
    }

    @Override public void createNewItem() {
        if (view == null) return;
        view.showCreateItemUi();
    }

    @Override public void subscribe() {
        load(true);
    }

    @Override public void unsubscribe() {
        entities.clear();
    }
}
