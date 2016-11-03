package fr.xgouchet.chronorg.data.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.TimelineIOProvider;
import fr.xgouchet.chronorg.data.models.Timeline;
import fr.xgouchet.chronorg.data.queriers.TimelineContentQuerier;
import rx.Observable;
import rx.Subscriber;

/**
 * @author Xavier Gouchet
 */
@Trace
public class TimelineRepository extends BaseRepository<Timeline> {

    public TimelineRepository(@NonNull Context context,
                              @NonNull TimelineIOProvider provider) {
        super(context, provider);
    }

    public Observable<Timeline> get(final int id) {
        return Observable.create(new Observable.OnSubscribe<Timeline>() {
            @Override public void call(final Subscriber<? super Timeline> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    TimelineContentQuerier eventContentQuerier = (TimelineContentQuerier) provider.provideQuerier();

                    eventContentQuerier.queryFull(contentResolver,
                            new SubscriberWrapperAction<>(subscriber), id);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }

    public Observable<Timeline> getTimelinesInProject(final int projectId) {
        return Observable.create(new Observable.OnSubscribe<Timeline>() {
            @Override public void call(final Subscriber<? super Timeline> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    TimelineContentQuerier eventContentQuerier = (TimelineContentQuerier) provider.provideQuerier();

                    eventContentQuerier.queryInProject(contentResolver,
                            new SubscriberWrapperAction<>(subscriber), projectId);

                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }


    public Observable<Timeline> getFullTimelinesInProject(final int projectId) {
        return Observable.create(new Observable.OnSubscribe<Timeline>() {
            @Override public void call(final Subscriber<? super Timeline> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    TimelineContentQuerier eventContentQuerier = (TimelineContentQuerier) provider.provideQuerier();

                    eventContentQuerier.queryFullInProject(contentResolver,
                            new SubscriberWrapperAction<>(subscriber), projectId);

                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }
}
