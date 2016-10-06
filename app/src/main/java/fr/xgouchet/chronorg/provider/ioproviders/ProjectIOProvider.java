package fr.xgouchet.chronorg.provider.ioproviders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.provider.cursorreaders.BaseCursorReader;
import fr.xgouchet.chronorg.provider.cursorreaders.ProjectCursorReader;
import fr.xgouchet.chronorg.provider.cvwriters.BaseContentValuesWriter;
import fr.xgouchet.chronorg.provider.cvwriters.ProjectContentValuesWriter;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class ProjectIOProvider implements BaseIOProvider<Project> {

    @Override public BaseCursorReader<Project> provideReader(@NonNull Cursor cursor) {
        return new ProjectCursorReader(cursor);
    }

    @Override public BaseContentValuesWriter<Project> provideWriter() {
        return new ProjectContentValuesWriter();
    }

    public String selectById() {
        return ChronorgSchema.COL_ID + "=?";
    }
}
