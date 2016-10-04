package fr.xgouchet.chronorg.ui.base;

import android.support.annotation.NonNull;

/**
 * @author Xavier Gouchet
 */
public interface BaseView<P extends BasePresenter, T> {

    void setPresenter(P presenter);

    void setLoading(boolean active);

    void setEmpty();

    void setError();

    void setContent(@NonNull T content);
}
