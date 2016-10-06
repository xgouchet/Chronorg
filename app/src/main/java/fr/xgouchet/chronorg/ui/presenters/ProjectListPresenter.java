package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.util.Log;

import com.deezer.android.counsel.annotations.Trace;

import java.util.ArrayList;
import java.util.List;

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

    @NonNull /*package*/ final ProjectListContract.View view;

    @NonNull private final ProjectRepository projectRepository;

    @NonNull private final CompositeSubscription subscriptions;

    public ProjectListPresenter(@NonNull ProjectRepository projectRepository,
                                @NonNull ProjectListContract.View view) {
        this.projectRepository = projectRepository;
        this.view = view;
        projects = new ArrayList<>();
        subscriptions = new CompositeSubscription();
        view.setPresenter(this);
    }

    @Override public void load(boolean force) {
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
                        view.setError();
                    }

                    @Override public void onNext(List<Project> projects) {
                        onProjectsLoaded(projects);
                    }
                });

        subscriptions.add(subscription);
    }

    /*package*/ void onProjectsLoaded(List<Project> projects) {
        this.projects.addAll(projects);
        if (this.projects.isEmpty()) {
            view.setEmpty();
        } else {
            view.setContent(this.projects);
        }
    }

    @Override public void projectSelected(@NonNull Project project) {
        view.showProject(project);
    }

    @Override public void createProject() {
        view.showCreateUi();
    }

    @Override public void subscribe() {
        load(false);
    }

    @Override public void unsubscribe() {
        projects.clear();
    }
}
