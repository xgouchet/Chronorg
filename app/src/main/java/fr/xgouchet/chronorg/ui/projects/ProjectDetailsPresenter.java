package fr.xgouchet.chronorg.ui.projects;

import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.ProjectRepository;
import fr.xgouchet.chronorg.model.Project;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Xavier Gouchet
 */
public class ProjectDetailsPresenter implements ProjectDetailsContract.Presenter {

    @NonNull /*package*/ Project project;
    @NonNull /*package*/ final ProjectDetailsContract.View view;
    @NonNull private final ProjectRepository projectRepository;

    @NonNull private final CompositeSubscription subscriptions;

    public ProjectDetailsPresenter(@NonNull ProjectRepository projectRepository, Project project, @NonNull ProjectDetailsContract.View view) {
        this.projectRepository = projectRepository;
        this.project = project;
        this.view = view;
        subscriptions = new CompositeSubscription();
        view.setPresenter(this);
    }

    @Override public void subscribe() {
        load(true);
    }

    @Override public void unsubscribe() {

    }

    @Override public void load(boolean force) {
        if (!force) {
            view.setContent(project);
            return;
        }

        view.setLoading(true);

        Subscription subscription = projectRepository
                .getProject(project.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Project>() {
                    @Override public void onCompleted() {
                        view.setLoading(false);
                        view.setContent(project);
                    }

                    @Override public void onError(Throwable e) {
                        view.setLoading(false);
                        view.setError();
                    }

                    @Override public void onNext(Project project) {
                        onProjectLoaded(project);
                    }
                });

        subscriptions.add(subscription);
    }

    /*package*/ void onProjectLoaded(Project project) {
        if (project != null) this.project = project;
    }
}
