package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.contracts.presenters.BasePresenter;
import fr.xgouchet.chronorg.ui.contracts.views.BaseView;

/**
 * @author Xavier Gouchet
 */
public interface ProjectEditContract {

    interface Presenter extends BasePresenter<View, Project> {

        void saveProject(@NonNull String inputNameText);
    }

    interface View extends BaseView<Presenter, Project> {

        void projectSaved();

        void projectSaveError(Throwable e);

    }
}
