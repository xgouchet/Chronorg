package fr.xgouchet.chronorg.ui.contracts.presenters;

import android.support.annotation.NonNull;

/**
 * @author Xavier Gouchet
 */
public interface BasePresenter<V, T> {

    void setView(@NonNull V view);

    void subscribe();

    void unsubscribe();

    void load(boolean force);
}
