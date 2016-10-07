package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.fragments.BaseView;
import fr.xgouchet.chronorg.ui.presenters.BasePresenter;

/**
 * @author Xavier Gouchet
 */
public interface ProjectDetailsContract {
    interface Presenter extends BasePresenter {

        void deleteProject();
    }

    interface View extends BaseView<Presenter, Project> {

        void projectDeleteError(@NonNull Throwable e);

        void projectDeleted();
    }
}
