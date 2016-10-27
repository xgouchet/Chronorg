package fr.xgouchet.chronorg.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.viewholders.BaseViewHolder;
import fr.xgouchet.chronorg.ui.viewholders.ProjectViewHolder;

/**
 * @author Xavier Gouchet
 */
public class ProjectsAdapter extends BaseSimpleAdapter<Project, ProjectViewHolder> {

    @NonNull /*package*/ final BaseViewHolder.Listener<Project> listener;

    public ProjectsAdapter(@NonNull BaseViewHolder.Listener<Project> listener) {
        this.listener = listener;
    }

    @Override protected ProjectViewHolder instantiateViewHolder(int viewType, View view) {
        return new ProjectViewHolder(listener, view);
    }

    @Override protected int getLayout(int viewType) {
        return R.layout.item_project;
    }
}
