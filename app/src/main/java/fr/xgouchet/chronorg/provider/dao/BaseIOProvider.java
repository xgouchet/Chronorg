package fr.xgouchet.chronorg.provider.dao;

import android.database.Cursor;
import android.support.annotation.NonNull;

/**
 * @author Xavier Gouchet
 */
public interface BaseIOProvider<T> {

    BaseCursorReader<T> provideReader(@NonNull Cursor cursor);

    BaseContentValuesWriter<T> provideWriter();
}
