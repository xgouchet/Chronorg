package fr.xgouchet.chronorg.ui.projects;

import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.model.Project;
import fr.xgouchet.chronorg.ui.base.BasePresenter;
import fr.xgouchet.chronorg.ui.base.BaseView;

/**
 * @author Xavier Gouchet
 */
public class EditProjectContract {

    public static final int EMPTY = 1;

    interface View extends BaseView<Presenter, Project> {

        void projectSaved();

        void projectSaveError(Throwable e);

        void invalidName(int reason);
    }

    interface Presenter extends BasePresenter {

        void saveProject(@NonNull String inputNameText, @NonNull String inputDescText);
    }
}
