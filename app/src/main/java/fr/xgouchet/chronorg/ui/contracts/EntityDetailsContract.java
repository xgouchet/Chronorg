package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.fragments.BaseView;

/**
 * @author Xavier Gouchet
 */
public interface EntityDetailsContract {
    interface Presenter extends fr.xgouchet.chronorg.ui.presenters.Presenter {
        void setView(View view);

        void deleteEntity();

        void editEntity();
    }

    interface View extends BaseView<Presenter, Entity> {

        void entityDeleteError(@NonNull Throwable e);

        void entityDeleted();

        void showEditEntityUi(@NonNull Entity entity);
    }
}
