package fr.xgouchet.chronorg.provider.ioproviders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.provider.cursorreaders.BaseCursorReader;
import fr.xgouchet.chronorg.provider.cvwriters.BaseContentValuesWriter;

/**
 * @author Xavier Gouchet
 */
public interface BaseIOProvider<T> {

    BaseCursorReader<T> provideReader(@NonNull Cursor cursor);

    BaseContentValuesWriter<T> provideWriter();
}
