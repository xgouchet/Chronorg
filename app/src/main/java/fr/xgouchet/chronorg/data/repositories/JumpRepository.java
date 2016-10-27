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
public class JumpRepository extends BaseRepository<Jump> {

    public JumpRepository(@NonNull Context context,
                          @NonNull JumpIOProvider provider) {
        super(context, provider);
    }


    public Observable<Jump> getJumpsInEntity(final int entityId) {
        return Observable.create(new Observable.OnSubscribe<Jump>() {
            @Override public void call(final Subscriber<? super Jump> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    JumpContentQuerier jumpContentQuerier = (JumpContentQuerier) provider.provideQuerier();

                    jumpContentQuerier.queryInEntity(contentResolver,
                            new SubscriberWrapperAction<>(subscriber), entityId);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }


}
