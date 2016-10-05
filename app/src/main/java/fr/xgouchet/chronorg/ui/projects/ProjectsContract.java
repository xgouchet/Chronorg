package fr.xgouchet.chronorg.ui.projects;

import android.support.annotation.NonNull;

import java.util.List;

import fr.xgouchet.chronorg.model.Project;
import fr.xgouchet.chronorg.ui.base.BasePresenter;
import fr.xgouchet.chronorg.ui.base.BaseView;

/**
 * @author Xavier Gouchet
 */

public interface ProjectsContract {


    interface Presenter extends BasePresenter {

        void projectSelected(@NonNull Project project);

        void createProject();
    }

    interface View extends BaseView<Presenter, List<Project>> {

        void showCreateUi();

        void showProject(@NonNull Project project);
    }
}
