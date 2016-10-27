package fr.xgouchet.chronorg.data.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.ioproviders.IOProvider;
import rx.Observable;
import rx.Subscriber;

/**
 * @author Xavier Gouchet
 */
public class BaseRepository<T> {

    @NonNull /*package*/ final Context context;

    @NonNull /*package*/ final IOProvider<T> provider;

    public BaseRepository(@NonNull Context context, @NonNull IOProvider<T> provider) {
        this.context = context;
        this.provider = provider;
    }

    public Observable<T> getAll() {

        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override public void call(final Subscriber<? super T> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    provider.provideQuerier().queryAll(contentResolver, new SubscriberWrapperAction<>(subscriber));
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }

    public Observable<T> get(final int id) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override public void call(final Subscriber<? super T> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    provider.provideQuerier().query(contentResolver, new SubscriberWrapperAction<>(subscriber), id);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }

    public Observable<Void> save(@NonNull final T item) {

        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    boolean success = provider.provideQuerier().save(contentResolver, item);
                    if (success) {
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new RuntimeException("Unable to save item !"));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        });
    }

    public Observable<Void> delete(final @NonNull T item) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    boolean success = provider.provideQuerier().delete(contentResolver, item);
                    if (success) {
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new RuntimeException("Unable to delete item !"));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        });
    }
}
