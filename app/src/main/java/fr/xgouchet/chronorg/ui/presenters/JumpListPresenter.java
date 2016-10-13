package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import java.util.ArrayList;
import java.util.List;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.data.repositories.JumpRepository;
import fr.xgouchet.chronorg.ui.contracts.JumpListContract;
import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import rx.subscriptions.CompositeSubscription;

/**
 * @author Xavier Gouchet
 */
@Trace
public class JumpListPresenter implements JumpListContract.Presenter {

    @NonNull private final List<Jump> jumps;
    @NonNull private final JumpRepository jumpRepository;
    @NonNull private final CompositeSubscription subscriptions;

    @Nullable /*package*/ JumpListContract.View view;
    @Nullable private Entity entity;

    public JumpListPresenter(@NonNull JumpRepository jumpRepository) {
        this.jumpRepository = jumpRepository;
        jumps = new ArrayList<>();
        subscriptions = new CompositeSubscription();
    }

    public void setEntity(@NonNull Entity entity) {
        this.entity = entity;
    }

    public void setView(@NonNull JumpListContract.View view) {
        this.view = view;
        view.setPresenter(this);
    }


    @Override public void load(boolean force) {
        if (view == null) return;
        if (entity == null) return;

        if (!force) {
            view.setContent(jumps);
        }

        view.setLoading(true);

        jumps.clear();

        Subscription subscription = jumpRepository
                .getJumpsInEntity(entity.getId())
                .toList()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Jump>>() {
                    @Override public void onCompleted() {
                        view.setLoading(false);
                    }

                    @Override public void onError(Throwable e) {
                        view.setLoading(false);
                        view.setError(e);
                    }

                    @Override public void onNext(List<Jump> jumps) {
                        onJumpsLoaded(jumps);
                    }
                });

        subscriptions.add(subscription);
    }

    /*package*/ void onJumpsLoaded(List<Jump> jumps) {
        this.jumps.addAll(jumps);
        if (view == null) return;

        if (this.jumps.isEmpty()) {
            view.setEmpty();
        } else {
            view.setContent(this.jumps);
        }
    }

    @Override public void itemSelected(@NonNull Jump jump) {
        if (view == null) return;
        view.showItem(jump);
    }

    @Override public void createNewItem() {
        if (view == null) return;
        view.showCreateItemUi();
    }

    @Override public void subscribe() {
        load(true);
    }

    @Override public void unsubscribe() {
        jumps.clear();
    }
}
