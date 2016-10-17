package fr.xgouchet.chronorg.data.writers;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class EventContentValuesWriter extends BaseContentValuesWriter<Event> {


    @Override public void fillContentValues(@NonNull ContentValues cv, @NonNull Event event) {
        cv.put(ChronorgSchema.COL_PROJECT_ID, event.getProjectId());
        cv.put(ChronorgSchema.COL_NAME, event.getName());
        cv.put(ChronorgSchema.COL_DESCRIPTION, event.getDescription());
        cv.put(ChronorgSchema.COL_INSTANT, event.getInstant().toString());
        cv.put(ChronorgSchema.COL_COLOR, event.getColor());
    }
}
