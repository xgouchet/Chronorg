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
import rx.functions.Action1;

/**
 * @author Xavier Gouchet
 */
@Trace
public class EventRepository {

    @NonNull /*package*/ final Context context;

    @NonNull /*package*/ final EventIOProvider provider;

    public EventRepository(@NonNull Context context,
                           @NonNull EventIOProvider provider) {
        this.context = context;
        this.provider = provider;
    }

    public Observable<Event> getEvents() {

        return Observable.create(new Observable.OnSubscribe<Event>() {
            @Override public void call(final Subscriber<? super Event> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    provider.provideQuerier().queryAll(contentResolver, new Action1<Event>() {
                        @Override public void call(Event event) {
                            subscriber.onNext(event);
                        }
                    });
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }

    public Observable<Event> getEvent(final int eventId) {
        return Observable.create(new Observable.OnSubscribe<Event>() {
            @Override public void call(final Subscriber<? super Event> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    provider.provideQuerier().query(contentResolver, new Action1<Event>() {
                        @Override public void call(Event event) {
                            subscriber.onNext(event);
                        }
                    }, eventId);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }


    public Observable<Event> getEventsInProject(final int projectId) {
        return Observable.create(new Observable.OnSubscribe<Event>() {
            @Override public void call(final Subscriber<? super Event> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    EventContentQuerier eventContentQuerier = (EventContentQuerier) provider.provideQuerier();

                    eventContentQuerier.queryInProject(contentResolver, new Action1<Event>() {
                        @Override public void call(Event event) {
                            subscriber.onNext(event);
                        }
                    }, projectId);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }


    public Observable<Void> saveEvent(@NonNull final Event event) {

        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    boolean success = provider.provideQuerier().save(contentResolver, event);
                    if (success) {
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new RuntimeException("Unable to save event !"));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        });
    }

    public Observable<Void> deleteEvent(final @NonNull Event event) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    boolean success = provider.provideQuerier().delete(contentResolver, event);
                    if (success) {
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new RuntimeException("Unable to delete event !"));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        });
    }
}
