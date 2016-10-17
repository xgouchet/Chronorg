package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.models.TimelineShard;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.data.transformers.EntityToSegmentFlatMap;
import fr.xgouchet.chronorg.data.transformers.SegmentToShardFlatMap;
import fr.xgouchet.chronorg.ui.contracts.TimelineContract;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Xavier Gouchet
 */
public class TimelinePresenter implements TimelineContract.Presenter {

    @NonNull private final EntityRepository entityRepository;
    @NonNull private final CompositeSubscription subscriptions;
    @NonNull private final ArrayList<TimelineShard> shards;

    @Nullable /*package*/ TimelineContract.View view;
    @Nullable private Project project;

    public TimelinePresenter(@NonNull EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
        subscriptions = new CompositeSubscription();
        shards = new ArrayList<>();
    }

    @Override public void setProject(@NonNull Project project) {
        this.project = project;
    }

    @Override public void setView(@NonNull TimelineContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void load(boolean force) {
        if (view == null) return;
        if (project == null) return;

        if (!force) {
            view.setContent(shards);
        }

        view.setLoading(true);
        shards.clear();

        Subscription subscription = entityRepository
                .getFullEntitiesInProject(project.getId())
                .flatMap(new EntityToSegmentFlatMap())
                .toSortedList()
                .flatMap(new SegmentToShardFlatMap())
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<TimelineShard>>() {
                    @Override public void onCompleted() {
                        view.setLoading(false);
                    }

                    @Override public void onError(Throwable e) {
                        view.setError(e);
                    }

                    @Override public void onNext(List<TimelineShard> shards) {
                        onShardsLoaded(shards);
                    }
                });

        subscriptions.add(subscription);
    }

    /*package*/ void onShardsLoaded(List<TimelineShard> shards) {
        this.shards.addAll(shards);
        if (view == null) return;

        if (this.shards.isEmpty()) {
            view.setEmpty();
        } else {
            view.setContent(this.shards);
        }
    }

    @Override public void subscribe() {
        load(true);
    }

    @Override public void unsubscribe() {
        shards.clear();
    }

    @Override public void createNewItem() {
        // Ignore
    }

    @Override public void itemSelected(@NonNull TimelineShard item) {
        // Ignore
    }
}
