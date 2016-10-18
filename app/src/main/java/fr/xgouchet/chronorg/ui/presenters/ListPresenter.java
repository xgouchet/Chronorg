package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;

/**
 * @author Xavier Gouchet
 */
public interface ListPresenter<T>  extends Presenter {

    void itemSelected(@NonNull T item);

    void createNewItem();
}
