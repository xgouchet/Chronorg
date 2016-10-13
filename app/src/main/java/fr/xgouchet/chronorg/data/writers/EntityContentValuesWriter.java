package fr.xgouchet.chronorg.data.writers;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import org.joda.time.ReadableInstant;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class EntityContentValuesWriter extends BaseContentValuesWriter<Entity> {


    @Override public void fillContentValues(@NonNull ContentValues cv, @NonNull Entity entity) {
        cv.put(ChronorgSchema.COL_PROJECT_ID, entity.getProjectId());
        cv.put(ChronorgSchema.COL_NAME, entity.getName());
        cv.put(ChronorgSchema.COL_DESCRIPTION, entity.getDescription());
        cv.put(ChronorgSchema.COL_BIRTH, entity.getBirth().toString());
        final ReadableInstant death = entity.getDeath();
        cv.put(ChronorgSchema.COL_DEATH, death == null ? null : death.toString());
        cv.put(ChronorgSchema.COL_COLOUR, entity.getColour());
    }
}
