package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import javax.inject.Inject;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.repositories.ProjectRepository;
import rx.Observable;

/**
 * @author Xavier Gouchet
 */
@Trace
public class ProjectListPresenter extends BaseListPresenter<Project> {

    @NonNull private final ProjectRepository projectRepository;

    @Inject
    public ProjectListPresenter(@NonNull ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override protected Observable<Project> getItemsObservable() {
        return projectRepository.getAll();
    }
}
