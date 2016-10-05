package fr.xgouchet.chronorg.ui.projects;

import android.support.annotation.NonNull;
import android.text.TextUtils;

import fr.xgouchet.chronorg.data.ProjectRepository;
import fr.xgouchet.chronorg.model.Project;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Xavier Gouchet
 */
public class EditProjectPresenter implements EditProjectContract.Presenter {


    @NonNull private final Project project;

    @NonNull /*package*/ final EditProjectContract.View view;

    @NonNull private final ProjectRepository projectRepository;

    public EditProjectPresenter(@NonNull ProjectRepository projectRepository,
                                @NonNull EditProjectContract.View view,
                                @NonNull Project project) {
        this.projectRepository = projectRepository;
        this.project = project;
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void subscribe() {
        view.setContent(project);
    }

    @Override public void unsubscribe() {

    }

    @Override public void load(boolean force) {
        // empty
    }

    @Override
    public void saveProject(@NonNull String inputNameText, @NonNull String inputDescText) {

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
