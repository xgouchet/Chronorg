package fr.xgouchet.chronorg.data.repositories;

import android.support.annotation.NonNull;

import rx.Subscriber;
import rx.functions.Action1;

/**
 * @author Xavier Gouchet
 */
public class SubscriberWrapperAction<T> implements Action1<T> {

    @NonNull private final Subscriber<? super T> subscriber;

    public SubscriberWrapperAction(@NonNull Subscriber<? super T> subscriber) {
        this.subscriber = subscriber;
    }

    @Override public void call(T t) {
        subscriber.onNext(t);
    }
}
