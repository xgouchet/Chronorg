package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;

/**
 * @author Xavier Gouchet
 */
public interface BaseListPresenter<T>  extends BasePresenter{

    void itemSelected(@NonNull T item);

    void createNewItem();
}
