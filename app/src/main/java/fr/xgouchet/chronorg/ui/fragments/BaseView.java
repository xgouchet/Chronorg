package fr.xgouchet.chronorg.ui.fragments;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.ui.presenters.Presenter;

/**
 * @author Xavier Gouchet
 */
public interface BaseView<P extends Presenter, T> {

    void setPresenter(@NonNull P presenter);

    void setError(@Nullable Throwable throwable);

    void setContent(@NonNull T content);
}
