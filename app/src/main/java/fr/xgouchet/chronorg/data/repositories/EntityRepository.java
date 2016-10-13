package fr.xgouchet.chronorg.data.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.ioproviders.EntityIOProvider;
import fr.xgouchet.chronorg.data.queriers.EntityContentQuerier;
import rx.Observable;
import rx.Subscriber;

/**
 * @author Xavier Gouchet
 */
@Trace
public class EntityRepository {

    @NonNull /*package*/ final Context context;

    @NonNull /*package*/ final EntityIOProvider provider;

    public EntityRepository(@NonNull Context context, @NonNull EntityIOProvider provider) {
        this.context = context;
        this.provider = provider;
    }


    public Observable<Entity> getEntities() {
        return Observable.create(new Observable.OnSubscribe<Entity>() {
            @Override public void call(Subscriber<? super Entity> subscriber) {
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

    public Observable<Entity> getEntitiesInProject(final int projectId) {
        return Observable.create(new Observable.OnSubscribe<Entity>() {
            @Override public void call(Subscriber<? super Entity> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    EntityContentQuerier entityBaseContentQuerier = (EntityContentQuerier) provider.provideQuerier();

                    entityBaseContentQuerier.queryInProject(contentResolver, subscriber, projectId);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }

    public Observable<Entity> getEntity(final int entityId) {
        return Observable.create(new Observable.OnSubscribe<Entity>() {
            @Override public void call(Subscriber<? super Entity> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    provider.provideQuerier().query(contentResolver, subscriber, entityId);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }

    public Observable<Void> saveEntity(@NonNull final Entity entity) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    boolean success = provider.provideQuerier().save(contentResolver, entity);

                    if (success) {
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new RuntimeException("Unable to saveEntity entity !"));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        });
    }

    public Observable<Void> deleteEntity(final @NonNull Entity entity) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    boolean success = provider.provideQuerier().delete(contentResolver, entity);

                    if (success) {
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new RuntimeException("Unable to delete entity !"));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        });
    }
}
