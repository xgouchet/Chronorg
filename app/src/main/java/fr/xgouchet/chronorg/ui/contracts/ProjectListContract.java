package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.NonNull;

import java.util.List;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.fragments.BaseView;
import fr.xgouchet.chronorg.ui.presenters.BasePresenter;

/**
 * @author Xavier Gouchet
 */

public interface ProjectListContract {


    interface Presenter extends BasePresenter {

        void projectSelected(@NonNull Project project);

        void createProject();
    }

    interface View extends BaseView<Presenter, List<Project>> {

        void showCreateUi();

        void showProject(@NonNull Project project);
    }
}
