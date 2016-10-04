package fr.xgouchet.chronorg.ui.base;

/**
 * @author Xavier Gouchet
 */
public interface BasePresenter {

    void subscribe();

    void unsubscribe();

    void load(boolean force);
}
