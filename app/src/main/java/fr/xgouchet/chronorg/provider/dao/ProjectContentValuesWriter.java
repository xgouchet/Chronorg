package fr.xgouchet.chronorg.provider.dao;

import android.content.ContentValues;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.model.Project;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class ProjectContentValuesWriter extends BaseContentValuesWriter<Project> {

    @Override public void fillContentValues(@NonNull ContentValues cv, @NonNull Project entity) {
        cv.put(ChronorgSchema.COL_NAME, entity.getName());
        cv.put(ChronorgSchema.COL_DESCRIPTION, entity.getDescription());
    }
}
