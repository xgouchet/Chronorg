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
        cv.put(ChronorgSchema.COL_BIRTH_INSTANT, entity.getBirth().toString());

        ReadableInstant death = entity.getDeath();
        cv.put(ChronorgSchema.COL_DEATH_INSTANT, death == null ? null : death.toString());
        cv.put(ChronorgSchema.COL_COLOR, entity.getColor());
    }
}
