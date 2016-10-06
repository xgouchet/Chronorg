package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.NonNull;

import java.util.List;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.fragments.BaseView;
import fr.xgouchet.chronorg.ui.presenters.BasePresenter;

/**
 * @author Xavier Gouchet
 */
public interface EntityListContract {

    interface Presenter extends BasePresenter {

        void entitySelected(@NonNull Entity entity);

        void createEntity();
    }

    interface View extends BaseView<Presenter, List<Entity>> {

        void showCreateUi();

        void showEntity(@NonNull Entity entity);
    }
}
