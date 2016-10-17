package fr.xgouchet.chronorg.data.ioproviders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.data.queriers.ContentQuerier;
import fr.xgouchet.chronorg.data.queriers.EventContentQuerier;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.readers.EventCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.data.writers.EventContentValuesWriter;

/**
 * @author Xavier Gouchet
 */
public class EventIOProvider implements IOProvider<Event> {

    @NonNull @Override public BaseCursorReader<Event> provideReader(@NonNull Cursor cursor) {
        return new EventCursorReader(cursor);
    }

    @NonNull @Override public BaseContentValuesWriter<Event> provideWriter() {
        return new EventContentValuesWriter();
    }

    @NonNull @Override public ContentQuerier<Event> provideQuerier() {
        return new EventContentQuerier(this);
    }
}
