package fr.xgouchet.chronorg.ui.contracts.presenters;

import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.ui.contracts.views.BaseEditView;
import rx.Subscriber;

/**
 * @author Xavier Gouchet
 */
@Trace
public interface BaseEditPresenter<T> extends BasePresenter<BaseEditView<T>, T> {

    void saveItem();

    void deleteItem();

    void setItemValue(@NonNull T item);

    public static class EditActionSubscriber extends Subscriber<Void> {
        @NonNull private final BaseEditView view;

        public EditActionSubscriber(@NonNull BaseEditView view) {
            this.view = view;
        }

        @Override public void onCompleted() {
            view.dismiss();
        }

        @Override public void onError(Throwable e) {
            view.onError(e);
        }

        @Override public void onNext(Void nothing) {
            // Ignore
        }
    }


}
