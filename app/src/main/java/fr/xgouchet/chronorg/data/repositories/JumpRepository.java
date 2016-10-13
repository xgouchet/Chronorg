package fr.xgouchet.chronorg.data.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.JumpIOProvider;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.data.queriers.JumpContentQuerier;
import rx.Observable;
import rx.Subscriber;

/**
 * @author Xavier Gouchet
 */
@Trace
public class JumpRepository {

    @NonNull /*package*/ final Context context;

    @NonNull /*package*/ final JumpIOProvider provider;

    public JumpRepository(@NonNull Context context,
                          @NonNull JumpIOProvider provider) {
        this.context = context;
        this.provider = provider;
    }

    public Observable<Jump> getJumps() {

        return Observable.create(new Observable.OnSubscribe<Jump>() {
            @Override public void call(Subscriber<? super Jump> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    provider.provideQuerier().queryAll(contentResolver, subscriber);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }

    public Observable<Jump> getJump(final int jumpId) {
        return Observable.create(new Observable.OnSubscribe<Jump>() {
            @Override public void call(Subscriber<? super Jump> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    provider.provideQuerier().query(contentResolver, subscriber, jumpId);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }


    public Observable<Jump> getJumpsInEntity(final int entityId) {
        return Observable.create(new Observable.OnSubscribe<Jump>() {
            @Override public void call(Subscriber<? super Jump> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    JumpContentQuerier jumpContentQuerier = (JumpContentQuerier) provider.provideQuerier();

                    jumpContentQuerier.queryInEntity(contentResolver, subscriber, entityId);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }


    public Observable<Void> saveJump(@NonNull final Jump jump) {

        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    boolean success = provider.provideQuerier().save(contentResolver, jump);
                    if (success) {
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new RuntimeException("Unable to save jump !"));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        });
    }

    public Observable<Void> deleteJump(final @NonNull Jump jump) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    boolean success = provider.provideQuerier().delete(contentResolver, jump);
                    if (success) {
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new RuntimeException("Unable to delete jump !"));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        });
    }
}
