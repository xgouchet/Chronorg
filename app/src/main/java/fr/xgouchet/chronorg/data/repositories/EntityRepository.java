package fr.xgouchet.chronorg.data.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.EntityIOProvider;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.queriers.EntityContentQuerier;
import rx.Observable;
import rx.Subscriber;

/**
 * @author Xavier Gouchet
 */
@Trace
public class EntityRepository extends BaseRepository<Entity> {

    public EntityRepository(@NonNull Context context, @NonNull EntityIOProvider provider) {
        super(context, provider);
    }

    public Observable<Entity> getEntitiesInProject(final int projectId) {
        return Observable.create(new Observable.OnSubscribe<Entity>() {
            @Override public void call(final Subscriber<? super Entity> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    EntityContentQuerier entityBaseContentQuerier = (EntityContentQuerier) provider.provideQuerier();

                    entityBaseContentQuerier.queryInProject(contentResolver,
                            new SubscriberWrapperAction<>(subscriber), projectId);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }

    public Observable<Entity> getFullEntitiesInProject(final int projectId) {
        return Observable.create(new Observable.OnSubscribe<Entity>() {
            @Override public void call(final Subscriber<? super Entity> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    EntityContentQuerier entityBaseContentQuerier = (EntityContentQuerier) provider.provideQuerier();

                    entityBaseContentQuerier.queryFullInProject(contentResolver,
                            new SubscriberWrapperAction<>(subscriber), projectId);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }
}
