package fr.xgouchet.chronorg.data.readers;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Timeline;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class TimelineCursorReader extends BaseCursorReader<Timeline> {


    private int idxId;
    private int idxProjectId;
    private int idxName;
    private int idxParentId;
    private int idxPortalId;
    private int idxDirection;

    public TimelineCursorReader(@NonNull Cursor cursor) {
        super(cursor);
    }

    @Override protected void cacheIndices() {
        idxId = getIndex(ChronorgSchema.COL_ID);
        idxProjectId = getIndex(ChronorgSchema.COL_PROJECT_ID);
        idxName = getIndex(ChronorgSchema.COL_NAME);
        idxParentId = getIndex(ChronorgSchema.COL_PARENT_ID);
        idxPortalId = getIndex(ChronorgSchema.COL_PORTAL_ID);
        idxDirection = getIndex(ChronorgSchema.COL_DIRECTION);
    }


    @NonNull @Override public Timeline instantiate() {
        return new Timeline();
    }

    @Override public void fill(@NonNull Timeline timeline) {
        timeline.setId(readInt(idxId));
        timeline.setProjectId(readInt(idxProjectId));
        timeline.setName(readString(idxName));
        timeline.setParentId(readInt(idxParentId));
        timeline.setPortalId(readInt(idxPortalId));
        //noinspection WrongConstant
        timeline.setDirection(readInt(idxDirection));
    }
}
