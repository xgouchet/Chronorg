package fr.xgouchet.chronorg.ui.projects;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

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

public class ProjectsPresenter implements ProjectsContract.Presenter {

    private List<Project> projects = new ArrayList<>();

    private ProjectsContract.View projectsView;

    private ProjectRepository projectRepository;

    private CompositeSubscription subscriptions;

    public ProjectsPresenter(ProjectRepository projectRepository, ProjectsContract.View projectsView) {
        this.projectRepository = projectRepository;
        this.projectsView = projectsView;

        subscriptions = new CompositeSubscription();
        projectsView.setPresenter(this);
    }

    @Override public void load(boolean force) {
        projectsView.setLoading(true);

        projects.clear();

        Subscription subscription = projectRepository
                .getProjects()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Project>>() {
                    @Override public void onCompleted() {
                        projectsView.setLoading(false);
                    }

                    @Override public void onError(Throwable e) {
                        Log.e("Hey", "Ho", e);
                        projectsView.setLoading(false);
                        projectsView.setError();
                    }

                    @Override public void onNext(List<Project> projects) {
                        if (projects.isEmpty()) {
                            projectsView.setEmpty();
                        } else {
                            projectsView.setContent(projects);
                        }
                    }
                });

        subscriptions.add(subscription);
    }

    @Override public void open(Project project) {
        // TODO
    }

    @Override public void createProject() {
        // TODO tablet version
        projectsView.showCreateUi();
    }

    @Override public void subscribe() {
        load(false);
    }

    @Override public void unsubscribe() {
        projects.clear();
    }
}
