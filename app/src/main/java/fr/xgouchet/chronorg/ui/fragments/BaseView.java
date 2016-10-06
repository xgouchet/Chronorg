package fr.xgouchet.chronorg.ui.fragments;

import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.ui.presenters.BasePresenter;

/**
 * @author Xavier Gouchet
 */
public interface BaseView<P extends BasePresenter, T> {

    void setPresenter(@NonNull P presenter);

    void setLoading(boolean active);

    void setEmpty();

    void setError();

    void setContent(@NonNull T content);
}
