package fr.xgouchet.chronorg.provider.ioproviders;

import android.database.Cursor;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.provider.queriers.BaseContentQuerier;
import fr.xgouchet.chronorg.provider.queriers.ProjectContentQuerier;
import fr.xgouchet.chronorg.provider.readers.BaseCursorReader;
import fr.xgouchet.chronorg.provider.readers.ProjectCursorReader;
import fr.xgouchet.chronorg.provider.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.provider.writers.ProjectContentValuesWriter;

/**
 * @author Xavier Gouchet
 */
public class ProjectIOProvider implements BaseIOProvider<Project> {

    @NonNull @Override public BaseCursorReader<Project> provideReader(@NonNull Cursor cursor) {
        return new ProjectCursorReader(cursor);
    }

    @NonNull @Override public BaseContentValuesWriter<Project> provideWriter() {
        return new ProjectContentValuesWriter();
    }

    @NonNull @Override
    public BaseContentQuerier<Project> provideQuerier() {
        return new ProjectContentQuerier(this);
    }
}
