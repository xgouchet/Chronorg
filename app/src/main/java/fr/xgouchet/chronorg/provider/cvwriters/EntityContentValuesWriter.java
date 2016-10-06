package fr.xgouchet.chronorg.provider.cvwriters;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class EntityContentValuesWriter extends BaseContentValuesWriter<Entity> {


    @Override public void fillContentValues(@NonNull ContentValues cv, @NonNull Entity entity) {
        cv.put(ChronorgSchema.COL_PROJECT_ID, entity.getName());
        cv.put(ChronorgSchema.COL_NAME, entity.getName());
        cv.put(ChronorgSchema.COL_DESCRIPTION, entity.getDescription());
        cv.put(ChronorgSchema.COL_BIRTH, entity.getBirth().toString());
        cv.put(ChronorgSchema.COL_DEATH, entity.getDeath().toString());
    }
}
