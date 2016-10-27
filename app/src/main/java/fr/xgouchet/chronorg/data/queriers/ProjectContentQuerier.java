package fr.xgouchet.chronorg.data.queriers;

import android.net.Uri;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.IOProvider;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
@Trace
public class ProjectContentQuerier extends BaseContentQuerier<Project> {


    public ProjectContentQuerier(@NonNull IOProvider<Project> provider) {
        super(provider);
    }

    @NonNull @Override protected Uri getUri() {
        return ChronorgSchema.PROJECTS_URI;
    }

    @Override protected int getId(@NonNull Project project) {
        return project.getId();
    }

    @Override
    protected String selectById() {
        return ChronorgSchema.COL_ID + "=?";
    }

    @Override protected String defaultOrder() {
        return ChronorgSchema.COL_NAME + " ASC";
    }
}
