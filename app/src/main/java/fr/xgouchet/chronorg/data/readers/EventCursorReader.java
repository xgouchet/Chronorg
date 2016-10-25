package fr.xgouchet.chronorg.data.readers;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class EventCursorReader extends BaseCursorReader<Event> {


    private int idxId;
    private int idxProjectId;
    private int idxName;
    private int idxInstant;
    private int idxColor;

    public EventCursorReader(@NonNull Cursor cursor) {
        super(cursor);
    }

    @Override protected void cacheIndices() {
        idxId = getIndex(ChronorgSchema.COL_ID);
        idxProjectId = getIndex(ChronorgSchema.COL_PROJECT_ID);
        idxName = getIndex(ChronorgSchema.COL_NAME);
        idxInstant = getIndex(ChronorgSchema.COL_INSTANT);
        idxColor = getIndex(ChronorgSchema.COL_COLOR);
    }


    @NonNull @Override public Event instantiate() {
        return new Event();
    }

    @Override public void fill(@NonNull Event event) {
        event.setId(readInt(idxId));
        event.setProjectId(readInt(idxProjectId));
        event.setName(readString(idxName));
        event.setInstant(readString(idxInstant));
        event.setColor(readInt(idxColor));
    }
}
