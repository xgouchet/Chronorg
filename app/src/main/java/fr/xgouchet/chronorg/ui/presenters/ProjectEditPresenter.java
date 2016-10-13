package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.repositories.ProjectRepository;
import fr.xgouchet.chronorg.ui.contracts.EditProjectContract;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Xavier Gouchet
 */
@Trace
public class ProjectEditPresenter implements EditProjectContract.Presenter {


    @Nullable private Project project;

    @Nullable /*package*/ EditProjectContract.View view;

    @NonNull private final ProjectRepository projectRepository;

    public ProjectEditPresenter(@NonNull ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    public void setProject(@NonNull Project project) {
        this.project = project;
    }

    public void setView(@NonNull EditProjectContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void subscribe() {
        if (view == null) return;
        if (project == null) return;

        view.setContent(project);
    }

    @Override public void unsubscribe() {

    }

    @Override public void load(boolean force) {
        // empty
    }

    @Override
    public void saveProject(@NonNull String inputNameText, @NonNull String inputDescText) {
        if (view == null) return;
        if (project == null) return;

        // check input
        if (TextUtils.isEmpty(inputNameText)) {
            view.invalidName(EditProjectContract.EMPTY);
            return;
        }

        project.setName(inputNameText);
        project.setDescription(inputDescText);
        projectRepository.saveProject(project)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Void>() {
                    @Override public void onCompleted() {
                        view.projectSaved();
                    }

                    @Override public void onError(Throwable e) {
                        view.projectSaveError(e);
                    }

                    @Override public void onNext(Void nothing) {
                        // Ignore
                    }
                });
    }
}