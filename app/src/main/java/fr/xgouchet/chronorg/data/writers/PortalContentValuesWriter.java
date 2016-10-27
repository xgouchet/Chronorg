package fr.xgouchet.chronorg.data.writers;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class PortalContentValuesWriter extends BaseContentValuesWriter<Portal> {


    @Override public void fillContentValues(@NonNull ContentValues cv, @NonNull Portal portal) {
        cv.put(ChronorgSchema.COL_PROJECT_ID, portal.getProjectId());
        cv.put(ChronorgSchema.COL_NAME, portal.getName());
        cv.put(ChronorgSchema.COL_DELAY, portal.getDelay().toString());
        cv.put(ChronorgSchema.COL_DIRECTION, portal.getDirection());
        cv.put(ChronorgSchema.COL_TIMELINE, portal.isTimeline() ? 1 : 0);
        cv.put(ChronorgSchema.COL_COLOR, portal.getColor());
    }
}
