package fr.xgouchet.chronorg.ui.contracts;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.fragments.BaseListView;
import fr.xgouchet.chronorg.ui.presenters.BaseListPresenter;

/**
 * @author Xavier Gouchet
 */
public interface EntityListContract {

    interface Presenter extends BaseListPresenter<Entity> {

    }

    interface View extends BaseListView<Presenter, Entity> {

    }
}
