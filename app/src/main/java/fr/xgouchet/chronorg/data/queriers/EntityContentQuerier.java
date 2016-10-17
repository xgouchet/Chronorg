package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.ioproviders.BaseIOProvider;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.functions.Action1;

/**
 * @author Xavier Gouchet
 */
public class EntityContentQuerier implements BaseContentQuerier<Entity> {

    @NonNull private final BaseIOProvider<Entity> provider;

    @NonNull private final JumpContentQuerier jumpContentQuerier;

    public EntityContentQuerier(@NonNull BaseIOProvider<Entity> provider,
                                @NonNull JumpContentQuerier jumpContentQuerier) {
        this.provider = provider;
        this.jumpContentQuerier = jumpContentQuerier;
    }

    @Override
    public void queryAll(@NonNull ContentResolver contentResolver,
                         @NonNull Action1<Entity> action) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.ENTITIES_URI,
                    null,
                    null,
                    null,
                    orderByName());

            readEntities(contentResolver, action, cursor, false);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override
    public void query(@NonNull ContentResolver contentResolver,
                      @NonNull Action1<Entity> action,
                      int entityId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.ENTITIES_URI,
                    null,
                    selectById(),
                    new String[]{Integer.toString(entityId)},
                    orderByName());

            readEntities(contentResolver, action, cursor, false);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public void queryInProject(@NonNull ContentResolver contentResolver,
                               @NonNull Action1<Entity> action,
                               int projectId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.ENTITIES_URI,
                    null,
                    selectByProjectId(),
                    new String[]{Integer.toString(projectId)},
                    orderByName());

            readEntities(contentResolver, action, cursor, false);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public void queryFullInProject(@NonNull ContentResolver contentResolver,
                                   @NonNull Action1<Entity> action,
                                   int projectId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.ENTITIES_URI,
                    null,
                    selectByProjectId(),
                    new String[]{Integer.toString(projectId)},
                    orderByName());

            readEntities(contentResolver, action, cursor, true);
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

    private void readEntities(@NonNull ContentResolver contentResolver,
                              @NonNull Action1<Entity> subscriber,
                              @Nullable Cursor cursor, boolean full) {
        if (cursor != null && cursor.getCount() > 0) {
            BaseCursorReader<Entity> reader = provider.provideReader(cursor);
            while (cursor.moveToNext()) {
                final Entity entity = reader.instantiateAndFill();
                if (full) {
                    readEntityJumps(contentResolver, entity);
                }
                subscriber.call(entity);
            }
        }
    }

    private void readEntityJumps(@NonNull ContentResolver contentResolver,
                                 @NonNull final Entity entity) {
        jumpContentQuerier.queryInEntity(contentResolver,
                new Action1<Jump>() {
                    @Override public void call(Jump jump) {
                        entity.jump(jump);
                    }
                },
                entity.getId());

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
