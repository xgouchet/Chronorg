package fr.xgouchet.chronorg.ui.fragments;

import android.content.Intent;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import java.util.ArrayList;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.activities.ProjectDetailsActivity;
import fr.xgouchet.chronorg.ui.activities.ProjectEditActivity;
import fr.xgouchet.chronorg.ui.adapters.ProjectsAdapter;

/**
 * @author Xavier Gouchet
 */
@Trace
public class ProjectListFragment extends BaseListFragment<Project, ProjectsAdapter> {

    @Override protected ProjectsAdapter getAdapter() {
        return new ProjectsAdapter(new ArrayList<Project>(), this);
    }

    @Override public void showCreateItemUi() {
        // TODO handle tablet
        Intent intent = ProjectEditActivity.intentNewProject(getActivity());
        getActivity().startActivity(intent);
    }

    @Override public void showItem(@NonNull Project item) {
        Intent intent = ProjectDetailsActivity.buildIntent(getActivity(), item);

        getActivity().startActivity(intent);
    }
}
