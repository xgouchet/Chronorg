package fr.xgouchet.chronorg.data.ioproviders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.data.queriers.BaseContentQuerier;
import fr.xgouchet.chronorg.data.queriers.JumpContentQuerier;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.readers.JumpCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.data.writers.JumpContentValuesWriter;

/**
 * @author Xavier Gouchet
 */
public class JumpIOProvider implements BaseIOProvider<Jump> {

    @NonNull @Override public BaseCursorReader<Jump> provideReader(@NonNull Cursor cursor) {
        return new JumpCursorReader(cursor);
    }

    @NonNull @Override public BaseContentValuesWriter<Jump> provideWriter() {
        return new JumpContentValuesWriter();
    }

    @NonNull @Override public BaseContentQuerier<Jump> provideQuerier() {
        return new JumpContentQuerier(this);
    }
}
