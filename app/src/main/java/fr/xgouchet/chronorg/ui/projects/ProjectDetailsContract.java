package fr.xgouchet.chronorg.ui.projects;

import fr.xgouchet.chronorg.model.Project;
import fr.xgouchet.chronorg.ui.base.BasePresenter;
import fr.xgouchet.chronorg.ui.base.BaseView;

/**
 * @author Xavier Gouchet
 */
public class ProjectDetailsContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter, Project> {

    }
}
