package fr.xgouchet.chronorg.provider.cvwriters;

import android.content.ContentValues;
import android.support.annotation.NonNull;

/**
 * @author Xavier Gouchet
 */
public abstract class BaseContentValuesWriter<T> {

    public ContentValues toContentValues(@NonNull T entity) {
        ContentValues cv = new ContentValues();
        fillContentValues(cv, entity);
        return cv;
    }

    public abstract void fillContentValues(@NonNull ContentValues cv, @NonNull T entity);
}
