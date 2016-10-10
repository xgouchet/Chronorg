package fr.xgouchet.chronorg.provider.readers;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
public class ProjectCursorReader extends BaseCursorReader<Project> {


    private int idxId;
    private int idxName;
    private int idxDesc;

    public ProjectCursorReader(@NonNull Cursor cursor) {
        super(cursor);
    }

    @Override protected void cacheIndices() {
        idxId = getIndex(ChronorgSchema.COL_ID);
        idxName = getIndex(ChronorgSchema.COL_NAME);
        idxDesc = getIndex(ChronorgSchema.COL_DESCRIPTION);
    }


    @NonNull @Override public Project instantiate() {
        return new Project();
    }

    @Override public void fill(@NonNull Project project) {
        project.setId(readInt(idxId));
        project.setName(readString(idxName));
        project.setDescription(readString(idxDesc));
    }
}
