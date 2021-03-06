package fr.xgouchet.chronorg.ui.contracts.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import java.util.ArrayList;
import java.util.List;

import fr.xgouchet.chronorg.ui.contracts.views.BaseListView;
import rx.Observable;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Xavier Gouchet
 */
@Trace
public abstract class BaseListPresenter<T> implements BasePresenter<BaseListView<T>, T> {

    @NonNull
    private final List<T> items;
    @Nullable
    private Subscription subscription;

    @Nullable /*package*/ BaseListView<T> view;

    public BaseListPresenter() {
        items = new ArrayList<>();
    }

    @Override
    public void setView(@NonNull BaseListView<T> view) {
        this.view = view;
        view.setPresenter(this);
    }


    @Override
    public void load(boolean force) {
        if (view == null) return;

        if (!force) {
            view.setContent(items);
        }

        view.setLoading(true);

        items.clear();

        if (subscription != null) subscription.unsubscribe();
        subscription = getItemsObservable()
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<T>>() {
                    @Override
                    public void onCompleted() {
                        view.setLoading(false);
                    }

                    @Override
                    public void onError(Throwable e) {
                        view.setLoading(false);
                        view.setError(e);
                    }

                    @Override
                    public void onNext(List<T> items) {
                        onItemsLoaded(items);
                    }
                });

    }

    protected abstract Observable<T> getItemsObservable();

    /*package*/ void onItemsLoaded(List<T> items) {
        this.items.addAll(items);
        if (view == null) return;

        if (this.items.isEmpty()) {
            view.setEmpty();
        } else {
            view.setContent(this.items);
        }
    }

    public void itemSelected(@NonNull T item) {
        if (view == null) return;
        view.showItem(item);
    }

    public void createNewItem() {
        if (view == null) return;
        view.showCreateItemUi();
    }

    @Override
    public void subscribe() {
        load(true);
    }

    @Override
    public void unsubscribe() {
        items.clear();
        if (subscription != null) subscription.unsubscribe();
    }
}
