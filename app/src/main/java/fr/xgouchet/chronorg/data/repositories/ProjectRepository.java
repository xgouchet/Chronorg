package fr.xgouchet.chronorg.data.repositories;

import android.content.Context;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.ioproviders.ProjectIOProvider;
import fr.xgouchet.chronorg.data.models.Project;

/**
 * @author Xavier Gouchet
 */
@Trace
public class ProjectRepository extends BaseRepository<Project> {

    public ProjectRepository(@NonNull Context context,
                             @NonNull ProjectIOProvider provider) {
        super(context, provider);
    }

}
