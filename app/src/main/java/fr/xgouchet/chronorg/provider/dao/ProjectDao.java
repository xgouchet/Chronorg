package fr.xgouchet.chronorg.provider.dao;

import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.model.Project;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class ProjectDao extends BaseDao<Project> {

    public ProjectDao(@NonNull SQLiteOpenHelper openHelper) {
        super(openHelper, ChronorgSchema.TABLE_PROJECTS);
    }

}
