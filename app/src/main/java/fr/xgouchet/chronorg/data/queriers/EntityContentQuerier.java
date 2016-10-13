package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import fr.xgouchet.chronorg.data.ioproviders.BaseIOProvider;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import rx.Subscriber;

/**
 * @author Xavier Gouchet
 */
public class EntityContentQuerier implements BaseContentQuerier<Entity> {

    @NonNull private final BaseIOProvider<Entity> provider;

    public EntityContentQuerier(@NonNull BaseIOProvider<Entity> provider) {
        this.provider = provider;
    }

    @Override
    public void queryAll(@NonNull ContentResolver contentResolver,
                         @NonNull Subscriber<? super Entity> subscriber) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.ENTITIES_URI,
                    null,
                    null,
                    null,
                    orderByName());

            readEntities(subscriber, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override
    public void query(@NonNull ContentResolver contentResolver,
                      @NonNull Subscriber<? super Entity> subscriber,
                      int entityId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.ENTITIES_URI,
                    null,
                    selectById(),
                    new String[]{Integer.toString(entityId)},
                    orderByName());

            readEntities(subscriber, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public void queryInProject(@NonNull ContentResolver contentResolver,
                               @NonNull Subscriber<? super Entity> subscriber,
                               int projectId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.ENTITIES_URI,
                    null,
                    selectByProjectId(),
                    new String[]{Integer.toString(projectId)},
                    orderByName());

            readEntities(subscriber, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override public boolean save(@NonNull ContentResolver contentResolver,
                                  @NonNull Entity entity) {
        BaseContentValuesWriter<Entity> writer = provider.provideWriter();
        ContentValues cv = writer.toContentValues(entity);

        if (entity.getId() <= 0) {
            Uri result = contentResolver.insert(ChronorgSchema.ENTITIES_URI, cv);
            return result != null;
        } else {
            int updated = contentResolver.update(ChronorgSchema.ENTITIES_URI,
                    cv,
                    selectById(),
                    new String[]{Integer.toString(entity.getId())});
            return updated > 0;
        }
    }

    @Override public boolean delete(@NonNull ContentResolver contentResolver,
                                    @NonNull Entity entity) {
        int deleted = contentResolver.delete(
                ChronorgSchema.ENTITIES_URI,
                selectById(),
                new String[]{Integer.toString(entity.getId())});
        return deleted != 0;
    }

    private void readEntities(@NonNull Subscriber<? super Entity> subscriber,
                              @Nullable Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            BaseCursorReader<Entity> reader = provider.provideReader(cursor);
            while (cursor.moveToNext()) {
                subscriber.onNext(reader.instantiateAndFill());
            }
        }
    }

    private String selectById() {
        return ChronorgSchema.COL_ID + "=?";
    }

    private String selectByProjectId() {
        return ChronorgSchema.COL_PROJECT_ID + "=?";
    }

    private String orderByName() {
        return ChronorgSchema.COL_NAME + " ASC";
    }
}
