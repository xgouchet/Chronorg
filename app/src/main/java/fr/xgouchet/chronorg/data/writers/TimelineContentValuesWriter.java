package fr.xgouchet.chronorg.data.writers;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Timeline;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class TimelineContentValuesWriter extends BaseContentValuesWriter<Timeline> {


    @Override public void fillContentValues(@NonNull ContentValues cv, @NonNull Timeline timeline) {
        cv.put(ChronorgSchema.COL_PROJECT_ID, timeline.getProjectId());
        cv.put(ChronorgSchema.COL_NAME, timeline.getName());
        cv.put(ChronorgSchema.COL_PARENT_ID, timeline.getParentId());
        cv.put(ChronorgSchema.COL_PORTAL_ID, timeline.getPortalId());
        cv.put(ChronorgSchema.COL_DIRECTION, timeline.getDirection());
    }
}
