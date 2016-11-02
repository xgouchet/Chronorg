package fr.xgouchet.chronorg.data.readers;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class PortalCursorReader extends BaseCursorReader<Portal> {


    private int idxId;
    private int idxProjectId;
    private int idxName;
    private int idxDelay;
    private int idxDirection;
    private int idxColor;

    public PortalCursorReader(@NonNull Cursor cursor) {
        super(cursor);
    }

    @Override protected void cacheIndices() {
        idxId = getIndex(ChronorgSchema.COL_ID);
        idxProjectId = getIndex(ChronorgSchema.COL_PROJECT_ID);
        idxName = getIndex(ChronorgSchema.COL_NAME);
        idxDelay = getIndex(ChronorgSchema.COL_DELAY);
        idxDirection = getIndex(ChronorgSchema.COL_DIRECTION);
        idxColor = getIndex(ChronorgSchema.COL_COLOR);
    }


    @NonNull @Override public Portal instantiate() {
        return new Portal();
    }

    @Override public void fill(@NonNull Portal portal) {
        portal.setId(readInt(idxId));
        portal.setProjectId(readInt(idxProjectId));
        portal.setName(readString(idxName));
        portal.setDelay(readString(idxDelay));
        //noinspection WrongConstant
        portal.setDirection(readInt(idxDirection));
        portal.setColor(readInt(idxColor));
    }
}
