package fr.xgouchet.chronorg.provider.cursorreaders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class EntityCursorReader extends BaseCursorReader<Entity> {

    private int idxId;
    private int idxProjectId;
    private int idxName;
    private int idxDesc;
    private int idxBirth;
    private int idxDeath;
    private int idxColour;

    public EntityCursorReader(@NonNull Cursor cursor) {
        super(cursor);
    }

    @Override protected void cacheIndices() {
        idxId = getIndex(ChronorgSchema.COL_ID);
        idxProjectId = getIndex(ChronorgSchema.COL_PROJECT_ID);
        idxName = getIndex(ChronorgSchema.COL_NAME);
        idxDesc = getIndex(ChronorgSchema.COL_DESCRIPTION);
        idxBirth = getIndex(ChronorgSchema.COL_BIRTH);
        idxDeath = getIndex(ChronorgSchema.COL_DEATH);
        idxColour = getIndex(ChronorgSchema.COL_COLOUR);
    }

    @NonNull @Override public Entity instantiate() {
        return new Entity();
    }

    @Override public void fill(@NonNull Entity entity) {
        entity.setId(readInt(idxId));
        entity.setProjectId(readInt(idxProjectId));
        entity.setName(readString(idxName));
        entity.setDescription(readString(idxDesc));
        entity.setBirth(readString(idxBirth));
        entity.setDeath(readString(idxDeath));
        entity.setColour(readInt(idxColour));
    }
}
