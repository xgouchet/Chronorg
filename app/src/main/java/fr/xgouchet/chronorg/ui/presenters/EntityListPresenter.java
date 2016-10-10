package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
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

    @NonNull /*package*/ final EntityListContract.View view;

    @NonNull private final EntityRepository entityRepository;

    @NonNull private final CompositeSubscription subscriptions;

    @NonNull private final Project project;

    public EntityListPresenter(@NonNull EntityRepository entityRepository,
                               @NonNull EntityListContract.View view,
                               @NonNull Project project) {
        this.view = view;
        this.entityRepository = entityRepository;
        this.project = project;
        entities = new ArrayList<>();
        subscriptions = new CompositeSubscription();

        view.setPresenter(this);
    }

    @Override public void load(boolean force) {
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
                        Log.e("Hey", "Ho", e);
                        view.setLoading(false);
                        view.setError(e);
                    }

                    @Override public void onNext(List<Entity> projects) {
                        onEntitiesLoaded(projects);
                    }
                });

        subscriptions.add(subscription);
    }

    /*package*/ void onEntitiesLoaded(List<Entity> entities) {
        this.entities.addAll(entities);
        if (this.entities.isEmpty()) {
            view.setEmpty();
        } else {
            view.setContent(this.entities);
        }
    }

    @Override public void itemSelected(@NonNull Entity entity) {
        view.showItem(entity);
    }

    @Override public void createNewItem() {
        view.showCreateItemUi();
    }

    @Override public void subscribe() {
        load(true);
    }

    @Override public void unsubscribe() {
        entities.clear();
    }
}
