package fr.xgouchet.chronorg.data.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.PortalIOProvider;
import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.data.queriers.PortalContentQuerier;
import rx.Observable;
import rx.Subscriber;

/**
 * @author Xavier Gouchet
 */
@Trace
public class PortalRepository extends BaseRepository<Portal> {

    public PortalRepository(@NonNull Context context,
                            @NonNull PortalIOProvider provider) {
        super(context, provider);
    }

    public Observable<Portal> getPortalsInProject(final int projectId) {
        return Observable.create(new Observable.OnSubscribe<Portal>() {
            @Override public void call(final Subscriber<? super Portal> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    PortalContentQuerier eventContentQuerier = (PortalContentQuerier) provider.provideQuerier();

                    eventContentQuerier.queryInProject(contentResolver,
                            new SubscriberWrapperAction<>(subscriber), projectId);

                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }


}
