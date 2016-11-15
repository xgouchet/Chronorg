package fr.xgouchet.chronorg.ui.contracts.views;

import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.ui.contracts.presenters.BaseEditPresenter;

/**
 * @author Xavier Gouchet
 */
public interface BaseEditView<T> extends BaseView<BaseEditPresenter<T>, T> {

    void dismiss();

    void onError(@Nullable Throwable t);
}
