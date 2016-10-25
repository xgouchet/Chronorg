package fr.xgouchet.chronorg.data.readers;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class JumpCursorReader extends BaseCursorReader<Jump> {


    private int idxId;
    private int idxEntityId;
    private int idxName;
    private int idxFrom;
    private int idxTo;
    private int idxOrder;

    public JumpCursorReader(@NonNull Cursor cursor) {
        super(cursor);
    }

    @Override protected void cacheIndices() {
        idxId = getIndex(ChronorgSchema.COL_ID);
        idxEntityId = getIndex(ChronorgSchema.COL_ENTITY_ID);
        idxName = getIndex(ChronorgSchema.COL_NAME);
        idxFrom = getIndex(ChronorgSchema.COL_FROM_INSTANT);
        idxTo = getIndex(ChronorgSchema.COL_TO_INSTANT);
        idxOrder = getIndex(ChronorgSchema.COL_ORDER);
    }


    @NonNull @Override public Jump instantiate() {
        return new Jump();
    }

    @Override public void fill(@NonNull Jump jump) {
        jump.setId(readInt(idxId));
        jump.setEntityId(readInt(idxEntityId));
        jump.setName(readString(idxName));
        jump.setFrom(readString(idxFrom));
        jump.setTo(readString(idxTo));
        jump.setOrder(readInt(idxOrder));
    }
}
