package fr.xgouchet.chronorg.ui.contracts;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.fragments.BaseView;
import fr.xgouchet.chronorg.ui.presenters.BasePresenter;

/**
 * @author Xavier Gouchet
 */
public interface ProjectDetailsContract {
    interface Presenter extends BasePresenter {

    }

    interface View extends BaseView<Presenter, Project> {

    }
}
