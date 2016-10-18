package fr.xgouchet.chronorg.ui.fragments;

import android.support.annotation.NonNull;

import java.util.List;

import fr.xgouchet.chronorg.ui.presenters.BaseListPresenter;

/**
 * @author Xavier Gouchet
 */
public interface BaseListView<T> extends BaseView<BaseListPresenter<T>, List<T>> {

    void setLoading(boolean active);

    void setEmpty();

    void showCreateItemUi();

    void showItem(@NonNull T item);
}
