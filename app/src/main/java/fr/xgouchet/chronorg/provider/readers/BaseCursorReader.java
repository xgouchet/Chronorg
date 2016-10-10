package fr.xgouchet.chronorg.provider.readers;

import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * @author Xavier Gouchet
 */
public abstract class BaseCursorReader<T> {

    @NonNull
    private final Cursor cursor;

    protected BaseCursorReader(@NonNull Cursor cursor) {
        this.cursor = cursor;
        cacheIndices();
    }

    public Cursor getCursor() {
        return cursor;
    }


    protected abstract void cacheIndices();

    @NonNull public abstract T instantiate();

    public abstract void fill(@NonNull T entity);

    @NonNull
    public T instantiateAndFill() {
        T entity = instantiate();
        fill(entity);
        return entity;
    }

    protected int getIndex(String columnName) {
        return cursor.getColumnIndex(columnName);
    }

    protected int readInt(int columnIndex) {
        return cursor.getInt(columnIndex);
    }

    protected String readString(int columnIndex) {
        return cursor.getString(columnIndex);
    }
}
