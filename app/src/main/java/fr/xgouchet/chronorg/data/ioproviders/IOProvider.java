package fr.xgouchet.chronorg.data.ioproviders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.queriers.ContentQuerier;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;

/**
 * @author Xavier Gouchet
 */
public interface IOProvider<T> {

    @NonNull BaseCursorReader<T> provideReader(@NonNull Cursor cursor);

    @NonNull BaseContentValuesWriter<T> provideWriter();

    @NonNull ContentQuerier<T> provideQuerier();
}
