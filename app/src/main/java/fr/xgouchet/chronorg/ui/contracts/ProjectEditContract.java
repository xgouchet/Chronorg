package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.fragments.BaseView;
import fr.xgouchet.chronorg.ui.presenters.BasePresenter;

/**
 * @author Xavier Gouchet
 */
public interface ProjectEditContract {

    public static final int EMPTY = 1;

    interface View extends BaseView<Presenter, Project> {

        void projectSaved();

        void projectSaveError(Throwable e);

        void invalidName(int reason);
    }

    interface Presenter extends BasePresenter {

        void setView(View view);

        void saveProject(@NonNull String inputNameText, @NonNull String inputDescText);
    }
}
