package fr.xgouchet.chronorg.provider.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.WorkerThread;

/**
 * @author Xavier Gouchet
 */
public abstract class BaseDao<T> {

    @NonNull private final SQLiteOpenHelper openHelper;
    @NonNull private final String tableName;

    public BaseDao(@NonNull SQLiteOpenHelper openHelper, @NonNull String tableName) {
        this.openHelper = openHelper;
        this.tableName = tableName;
    }

    @WorkerThread
    public long insert(ContentValues cv) {
        SQLiteDatabase db = openHelper.getWritableDatabase();

        long insertedId = -1;

        db.beginTransaction();
        try {
            insertedId = db.insert(tableName, null, cv);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return insertedId;
    }

    public Cursor query(@Nullable String[] projection,
                        @Nullable String selection,
                        @Nullable String[] selectionArgs,
                        @Nullable String sortOrder) {
        SQLiteDatabase db = openHelper.getReadableDatabase();

        return db.query(tableName, projection, selection, selectionArgs, null, null, sortOrder);
    }


    public int delete(@Nullable String selection,
                      @Nullable String[] selectionArgs) {
        SQLiteDatabase db = openHelper.getWritableDatabase();

        int deleted = 0;

        db.beginTransaction();
        try {
            deleted = db.delete(tableName, selection, selectionArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return deleted;
    }

    public int update(@Nullable ContentValues values,
                      @Nullable String selection,
                      @Nullable String[] selectionArgs) {
        if (values == null) return 0;
        int updated = 0;

        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.beginTransaction();
        try {
            updated = db.update(tableName, values, selection, selectionArgs);
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
        }

        return updated;
    }
}
