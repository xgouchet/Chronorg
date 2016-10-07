package fr.xgouchet.chronorg.ui.contracts;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.fragments.BaseListView;
import fr.xgouchet.chronorg.ui.presenters.BaseListPresenter;

/**
 * @author Xavier Gouchet
 */

public interface ProjectListContract {


    interface Presenter extends BaseListPresenter<Project> {


    }

    interface View extends BaseListView<Presenter, Project> {


    }
}
