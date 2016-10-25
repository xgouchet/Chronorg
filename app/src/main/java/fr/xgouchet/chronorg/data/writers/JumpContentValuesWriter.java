package fr.xgouchet.chronorg.data.writers;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class JumpContentValuesWriter extends BaseContentValuesWriter<Jump> {


    @Override public void fillContentValues(@NonNull ContentValues cv, @NonNull Jump jump) {
        cv.put(ChronorgSchema.COL_ENTITY_ID, jump.getEntityId());
        cv.put(ChronorgSchema.COL_NAME, jump.getName());
        cv.put(ChronorgSchema.COL_FROM_INSTANT, jump.getFrom().toString());
        cv.put(ChronorgSchema.COL_TO_INSTANT, jump.getTo().toString());
        cv.put(ChronorgSchema.COL_ORDER, jump.getOrder());
    }
}
