package fr.xgouchet.chronorg.provider.dao;

import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class EntityDao extends BaseDao<Entity> {

    public EntityDao(@NonNull SQLiteOpenHelper openHelper) {
        super(openHelper, ChronorgSchema.TABLE_ENTITIES);
    }
}
