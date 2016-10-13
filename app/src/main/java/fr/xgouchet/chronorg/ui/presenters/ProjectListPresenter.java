package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import com.deezer.android.counsel.annotations.Trace;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.repositories.ProjectRepository;
import fr.xgouchet.chronorg.ui.contracts.ProjectListContract;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Xavier Gouchet
 */
@Trace
public class ProjectListPresenter implements ProjectListContract.Presenter {


    @NonNull private final List<Project> projects;

    @Nullable /*package*/ ProjectListContract.View view;

    @NonNull private final ProjectRepository projectRepository;

    @NonNull private final CompositeSubscription subscriptions;

    @Inject
    public ProjectListPresenter(@NonNull ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        projects = new ArrayList<>();
        subscriptions = new CompositeSubscription();
    }

    public void setView(@NonNull ProjectListContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void load(boolean force) {
        if (view == null) return;

        if (!force) {
            view.setContent(projects);
        }

        view.setLoading(true);

        projects.clear();

        Subscription subscription = projectRepository
                .getProjects()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Project>>() {
                    @Override public void onCompleted() {
                        view.setLoading(false);
                    }

                    @Override public void onError(Throwable e) {
                        Log.e("Hey", "Ho", e);
                        view.setLoading(false);
                        view.setError(e);
                    }

                    @Override public void onNext(List<Project> projects) {
                        onProjectsLoaded(projects);
                    }
                });

        subscriptions.add(subscription);
    }

    /*package*/ void onProjectsLoaded(List<Project> projects) {
        this.projects.addAll(projects);
        if (view == null) return;

        if (this.projects.isEmpty()) {
            view.setEmpty();
        } else {
            view.setContent(this.projects);
        }
    }

    @Override public void itemSelected(@NonNull Project project) {
        if (view == null) return;
        view.showItem(project);
    }

    @Override public void createNewItem() {
        if (view == null) return;
        view.showCreateItemUi();
    }

    @Override public void subscribe() {
        load(false);
    }

    @Override public void unsubscribe() {
        projects.clear();
    }
}
