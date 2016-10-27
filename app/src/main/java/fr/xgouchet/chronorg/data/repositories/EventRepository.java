package fr.xgouchet.chronorg.data.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.EventIOProvider;
import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.data.queriers.EventContentQuerier;
import rx.Observable;
import rx.Subscriber;

/**
 * @author Xavier Gouchet
 */
@Trace
public class EventRepository extends BaseRepository<Event> {


    public EventRepository(@NonNull Context context,
                           @NonNull EventIOProvider provider) {
        super(context, provider);
    }


    public Observable<Event> getEventsInProject(final int projectId) {
        return Observable.create(new Observable.OnSubscribe<Event>() {
            @Override public void call(final Subscriber<? super Event> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    EventContentQuerier eventContentQuerier = (EventContentQuerier) provider.provideQuerier();

                    eventContentQuerier.queryInProject(contentResolver, new SubscriberWrapperAction<>(subscriber), projectId);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }


}
