package fr.xgouchet.chronorg.data.readers;

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
    private int idxBirth;
    private int idxDeath;
    private int idxColor;

    public EntityCursorReader(@NonNull Cursor cursor) {
        super(cursor);
    }

    @Override protected void cacheIndices() {
        idxId = getIndex(ChronorgSchema.COL_ID);
        idxProjectId = getIndex(ChronorgSchema.COL_PROJECT_ID);
        idxName = getIndex(ChronorgSchema.COL_NAME);
        idxBirth = getIndex(ChronorgSchema.COL_BIRTH_INSTANT);
        idxDeath = getIndex(ChronorgSchema.COL_DEATH_INSTANT);
        idxColor = getIndex(ChronorgSchema.COL_COLOR);
    }

    @NonNull @Override public Entity instantiate() {
        return new Entity();
    }

    @Override public void fill(@NonNull Entity entity) {
        entity.setId(readInt(idxId));
        entity.setProjectId(readInt(idxProjectId));
        entity.setName(readString(idxName));
        entity.setBirth(readString(idxBirth));
        entity.setDeath(readString(idxDeath));
        entity.setColor(readInt(idxColor));
    }
}
