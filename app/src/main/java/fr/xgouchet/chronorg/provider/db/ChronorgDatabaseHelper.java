package fr.xgouchet.chronorg.provider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.provider.dao.ProjectDao;

/**
 * @author Xavier Gouchet
 */
public class ChronorgDatabaseHelper extends SQLiteDescriptionHelper {

    @NonNull
    private final ProjectDao projectDao;

    /**
     * {@inheritDoc}
     */
    public ChronorgDatabaseHelper(@NonNull Context context, @NonNull SQLiteDescriptionProvider descriptionProvider, @Nullable SQLiteDatabase.CursorFactory factory) {
        super(context, descriptionProvider, factory);
        projectDao = new ProjectDao(this);
    }

    @NonNull
    public ProjectDao getProjectDao() {
        return projectDao;
    }
}
