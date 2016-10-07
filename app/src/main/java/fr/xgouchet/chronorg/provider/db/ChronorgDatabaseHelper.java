package fr.xgouchet.chronorg.provider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.provider.dao.EntityDao;
import fr.xgouchet.chronorg.provider.dao.ProjectDao;

/**
 * @author Xavier Gouchet
 */
public class ChronorgDatabaseHelper extends SQLiteDescriptionHelper {

    @NonNull private final ProjectDao projectDao;
    @NonNull private final EntityDao entityDao;


    /**
     * {@inheritDoc}
     */
    public ChronorgDatabaseHelper(@NonNull Context context, @NonNull SQLiteDescriptionProvider descriptionProvider, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, descriptionProvider, factory);
        projectDao = new ProjectDao(this);
        entityDao = new EntityDao(this);
    }

    @NonNull
    public ProjectDao getProjectDao() {
        return projectDao;
    }

    @NonNull public EntityDao getEntityDao() {
        return entityDao;
    }
}
