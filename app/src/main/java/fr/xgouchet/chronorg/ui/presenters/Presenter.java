package fr.xgouchet.chronorg.ui.presenters;

/**
 * @author Xavier Gouchet
 */
public interface Presenter {

    void subscribe();

    void unsubscribe();

    void load(boolean force);
}
