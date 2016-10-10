package fr.xgouchet.chronorg.provider.ioproviders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.provider.queriers.BaseContentQuerier;
import fr.xgouchet.chronorg.provider.readers.BaseCursorReader;
import fr.xgouchet.chronorg.provider.writers.BaseContentValuesWriter;

/**
 * @author Xavier Gouchet
 */
public interface BaseIOProvider<T> {

    @NonNull BaseCursorReader<T> provideReader(@NonNull Cursor cursor);

    @NonNull BaseContentValuesWriter<T> provideWriter();

    @NonNull BaseContentQuerier<T> provideQuerier();
}
