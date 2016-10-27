package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.IOProvider;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import rx.functions.Action1;

/**
 * @author Xavier Gouchet
 */
@Trace
public abstract class BaseContentQuerier<T> implements ContentQuerier<T> {

    @NonNull protected final IOProvider<T> ioProvider;

    protected BaseContentQuerier(@NonNull IOProvider<T> ioProvider) {
        this.ioProvider = ioProvider;
    }

    @Override
    public  void queryAll(@NonNull ContentResolver contentResolver,
                               @NonNull Action1<T> action) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(getUri(),
                    null,
                    null,
                    null,
                    defaultOrder());

            readRows(action, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override
    public void query(@NonNull ContentResolver contentResolver, @NonNull Action1<T> action, int id) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(getUri(),
                    null,
                    selectById(),
                    new String[]{Integer.toString(id)},
                    defaultOrder());

            readRows(action, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override
    public boolean save(@NonNull ContentResolver contentResolver, @NonNull T item) {
        BaseContentValuesWriter<T> writer = ioProvider.provideWriter();
        ContentValues cv = writer.toContentValues(item);

        final int id = getId(item);
        if (id <= 0) {
            Uri result = contentResolver.insert(getUri(), cv);
            return result != null;
        } else {
            int updated = contentResolver.update(getUri(),
                    cv,
                    selectById(),
                    new String[]{Integer.toString(id)});
            return updated > 0;
        }
    }

    @Override
    public boolean delete(@NonNull ContentResolver contentResolver, @NonNull T item) {
        int deleted = contentResolver.delete(
                getUri(),
                selectById(),
                new String[]{Integer.toString(getId(item))});
        return deleted != 0;
    }

    protected void readRows(@NonNull Action1<T> action,
                                  @Nullable Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            BaseCursorReader<T> reader = ioProvider.provideReader(cursor);
            while (cursor.moveToNext()) {
                action.call(reader.instantiateAndFill());
            }
        }
    }

    @NonNull protected abstract Uri getUri();

    @Nullable protected abstract String selectById();

    protected abstract int getId(@NonNull T item);

    @Nullable protected String defaultOrder() {
        return null;
    }
}
