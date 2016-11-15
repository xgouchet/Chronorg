package fr.xgouchet.chronorg.ui.contracts.views;

import android.support.annotation.NonNull;

import java.util.List;

import fr.xgouchet.chronorg.ui.contracts.presenters.BaseListPresenter;

/**
 * @author Xavier Gouchet
 */
public interface BaseListView<T> extends BaseView<BaseListPresenter<T>, List<T>> {

    void setLoading(boolean active);

    void setEmpty();

    void showCreateItemUi();

    void showItem(@NonNull T item);
}
