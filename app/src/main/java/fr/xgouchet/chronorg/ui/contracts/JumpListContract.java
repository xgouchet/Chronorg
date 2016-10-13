package fr.xgouchet.chronorg.ui.contracts;

import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.ui.fragments.BaseListView;
import fr.xgouchet.chronorg.ui.presenters.BaseListPresenter;

/**
 * @author Xavier Gouchet
 */
public interface JumpListContract {

    interface Presenter extends BaseListPresenter<Jump> {
        void setView(View view);
    }

    interface View extends BaseListView<Presenter, Jump> {

    }
}
