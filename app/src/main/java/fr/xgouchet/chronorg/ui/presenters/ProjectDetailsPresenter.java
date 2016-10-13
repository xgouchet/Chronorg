package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.repositories.ProjectRepository;
import fr.xgouchet.chronorg.ui.contracts.ProjectDetailsContract;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Xavier Gouchet
 */
@Trace
public class ProjectDetailsPresenter implements ProjectDetailsContract.Presenter {

    @Nullable /*package*/ Project project;
    @Nullable /*package*/ ProjectDetailsContract.View view;
    @NonNull private final ProjectRepository projectRepository;

    @NonNull private final CompositeSubscription subscriptions;

    public ProjectDetailsPresenter(@NonNull ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
        subscriptions = new CompositeSubscription();

    }

    public void setProject(@NonNull Project project) {
        this.project = project;
    }

    public void setView(@NonNull ProjectDetailsContract.View view) {
        this.view = view;
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

        Subscription subscription = projectRepository
                .getProject(project.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Project>() {
                    @Override public void onCompleted() {
                        view.setContent(project);
                    }

                    @Override public void onError(Throwable e) {
                        view.setError(e);
                    }

                    @Override public void onNext(Project project) {
                        onProjectLoaded(project);
                    }
                });

        subscriptions.add(subscription);
    }

    /*package*/ void onProjectLoaded(Project project) {
        if (project != null) {
            this.project = project;
            view.setContent(project);
        }
    }

    @Override public void editProject() {
        view.showEditProjectUi(project);
    }

    @Override public void deleteProject() {
        Subscription subscription = projectRepository
                .deleteProject(project)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Void>() {
                    @Override public void onCompleted() {
                        view.projectDeleted();
                    }

                    @Override public void onError(Throwable e) {
                        view.projectDeleteError(e);
                    }

                    @Override public void onNext(Void project) {
                    }
                });

        subscriptions.add(subscription);
    }
}
