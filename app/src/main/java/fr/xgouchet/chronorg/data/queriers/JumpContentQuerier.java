package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.ioproviders.BaseIOProvider;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.Subscriber;

/**
 * @author Xavier Gouchet
 */
public class JumpContentQuerier implements BaseContentQuerier<Jump> {

    @NonNull private final BaseIOProvider<Jump> provider;

    public JumpContentQuerier(@NonNull BaseIOProvider<Jump> provider) {
        this.provider = provider;
    }

    @Override
    public void queryAll(@NonNull ContentResolver contentResolver,
                         @NonNull Subscriber<? super Jump> subscriber) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.JUMPS_URI,
                    null,
                    null,
                    null,
                    naturalOrder());

            readEntities(subscriber, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override
    public void query(@NonNull ContentResolver contentResolver,
                      @NonNull Subscriber<? super Jump> subscriber,
                      int entityId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.JUMPS_URI,
                    null,
                    selectById(),
                    new String[]{Integer.toString(entityId)},
                    naturalOrder());

            readEntities(subscriber, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    public void queryInEntity(@NonNull ContentResolver contentResolver,
                              @NonNull Subscriber<? super Jump> subscriber,
                              int entityId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.JUMPS_URI,
                    null,
                    selectByEntityId(),
                    new String[]{Integer.toString(entityId)},
                    naturalOrder());

            readEntities(subscriber, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override public boolean save(@NonNull ContentResolver contentResolver,
                                  @NonNull Jump jump) {
        BaseContentValuesWriter<Jump> writer = provider.provideWriter();
        ContentValues cv = writer.toContentValues(jump);

        if (jump.getId() <= 0) {
            Uri result = contentResolver.insert(ChronorgSchema.JUMPS_URI, cv);
            return result != null;
        } else {
            int updated = contentResolver.update(ChronorgSchema.JUMPS_URI,
                    cv,
                    selectById(),
                    new String[]{Integer.toString(jump.getId())});
            return updated > 0;
        }
    }

    @Override public boolean delete(@NonNull ContentResolver contentResolver,
                                    @NonNull Jump jump) {
        int deleted = contentResolver.delete(
                ChronorgSchema.JUMPS_URI,
                selectById(),
                new String[]{Integer.toString(jump.getId())});
        return deleted != 0;
    }

    private void readEntities(@NonNull Subscriber<? super Jump> subscriber,
                              @Nullable Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            BaseCursorReader<Jump> reader = provider.provideReader(cursor);
            while (cursor.moveToNext()) {
                subscriber.onNext(reader.instantiateAndFill());
            }
        }
    }

    private String selectById() {
        return ChronorgSchema.COL_ID + "=?";
    }

    private String selectByEntityId() {
        return ChronorgSchema.COL_ENTITY_ID + "=?";
    }

    private String naturalOrder() {
        return ChronorgSchema.COL_ORDER + " ASC";
    }
}
