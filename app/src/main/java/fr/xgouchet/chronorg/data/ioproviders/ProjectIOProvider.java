package fr.xgouchet.chronorg.data.ioproviders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.queriers.ContentQuerier;
import fr.xgouchet.chronorg.data.queriers.ProjectContentQuerier;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.readers.ProjectCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.data.writers.ProjectContentValuesWriter;

/**
 * @author Xavier Gouchet
 */
public class ProjectIOProvider implements IOProvider<Project> {

    @NonNull @Override public BaseCursorReader<Project> provideReader(@NonNull Cursor cursor) {
        return new ProjectCursorReader(cursor);
    }

    @NonNull @Override public BaseContentValuesWriter<Project> provideWriter() {
        return new ProjectContentValuesWriter();
    }

    @NonNull @Override
    public ContentQuerier<Project> provideQuerier() {
        return new ProjectContentQuerier(this);
    }
}
