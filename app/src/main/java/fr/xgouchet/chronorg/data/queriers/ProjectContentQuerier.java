package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.ioproviders.BaseIOProvider;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.functions.Action1;

/**
 * @author Xavier Gouchet
 */
public class ProjectContentQuerier implements BaseContentQuerier<Project> {

    private final BaseIOProvider<Project> provider;

    public ProjectContentQuerier(@NonNull BaseIOProvider<Project> provider) {
        this.provider = provider;
    }

    @Override
    public void queryAll(@NonNull ContentResolver contentResolver,
                         @NonNull Action1<Project> action) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.PROJECTS_URI,
                    null,
                    null,
                    null,
                    orderByName());

            readProjects(action, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override
    public void query(@NonNull ContentResolver contentResolver,
                      @NonNull Action1<Project> action,
                      int projectId) {
        Cursor cursor = null;
        try {
            cursor = contentResolver.query(ChronorgSchema.PROJECTS_URI,
                    null,
                    selectById(),
                    new String[]{Integer.toString(projectId)},
                    orderByName());

            readProjects(action, cursor);
        } finally {
            if (cursor != null) cursor.close();
        }
    }

    @Override
    public boolean save(@NonNull ContentResolver contentResolver,
                        @NonNull Project project) {
        BaseContentValuesWriter<Project> writer = provider.provideWriter();
        ContentValues cv = writer.toContentValues(project);

        if (project.getId() <= 0) {
            Uri result = contentResolver.insert(ChronorgSchema.PROJECTS_URI, cv);
            return result != null;
        } else {
            int updated = contentResolver.update(ChronorgSchema.PROJECTS_URI,
                    cv,
                    selectById(),
                    new String[]{Integer.toString(project.getId())});
            return updated > 0;
        }
    }

    @Override
    public boolean delete(@NonNull ContentResolver contentResolver,
                          @NonNull Project project) {
        int deleted = contentResolver.delete(
                ChronorgSchema.PROJECTS_URI,
                selectById(),
                new String[]{Integer.toString(project.getId())});
        return deleted != 0;
    }


    private void readProjects(@NonNull Action1<Project> action,
                              @Nullable Cursor cursor) {
        if (cursor != null && cursor.getCount() > 0) {
            BaseCursorReader<Project> reader = provider.provideReader(cursor);
            while (cursor.moveToNext()) {
                action.call(reader.instantiateAndFill());
            }
        }
    }

    private String selectById() {
        return ChronorgSchema.COL_ID + "=?";
    }

    private String orderByName() {
        return ChronorgSchema.COL_NAME + " ASC";
    }
}
