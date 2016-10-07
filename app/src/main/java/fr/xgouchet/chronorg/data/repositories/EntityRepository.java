package fr.xgouchet.chronorg.data.repositories;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.provider.cursorreaders.BaseCursorReader;
import fr.xgouchet.chronorg.provider.cvwriters.BaseContentValuesWriter;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import fr.xgouchet.chronorg.provider.ioproviders.EntityIOProvider;
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

    public Observable<Entity> getEntities(final int projectId) {
        return Observable.create(new Observable.OnSubscribe<Entity>() {
            @Override public void call(Subscriber<? super Entity> subscriber) {
                Cursor cursor = null;
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    cursor = contentResolver.query(ChronorgSchema.projectEntitiesUri(projectId), null, null, null, null);

                    if (cursor != null && cursor.getCount() > 0) {
                        BaseCursorReader<Entity> reader = provider.provideReader(cursor);
                        while (cursor.moveToNext()) {
                            subscriber.onNext(reader.instantiateAndFill());
                            // TODO also read jumps


                        }
                    }
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                } finally {
                    if (cursor != null) cursor.close();
                }

            }
        });
    }

    public Observable<Void> saveEntity(@NonNull final Entity entity) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    BaseContentValuesWriter<Entity> writer = provider.provideWriter();

                    ContentValues cv = writer.toContentValues(entity);
                    Uri result = contentResolver.insert(ChronorgSchema.projectEntitiesUri(entity.getProjectId()), cv);

                    if (result == null) {
                        subscriber.onError(new RuntimeException("Unable to save entity !"));
                    } else {
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        });
    }
}
