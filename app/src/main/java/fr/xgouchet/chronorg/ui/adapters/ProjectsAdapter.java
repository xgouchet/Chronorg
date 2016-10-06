package fr.xgouchet.chronorg.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.viewholders.ProjectViewHolder;

/**
 * @author Xavier Gouchet
 */
public class ProjectsAdapter extends BaseSimpleAdapter<Project, ProjectViewHolder> {

    @NonNull /*package*/ final List<Project> projects;
    @NonNull private final ProjectViewHolder.Listener listener;

    public ProjectsAdapter(@NonNull List<Project> projects, @NonNull ProjectViewHolder.Listener listener) {
        this.projects = projects;
        this.listener = listener;
    }

    @Override public int getItemCount() {
        return projects.size();
    }

    public void update(@NonNull final List<Project> newContent) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override public int getOldListSize() {
                return projects.size();
            }

            @Override public int getNewListSize() {
                return newContent.size();
            }

            @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return projects.get(oldItemPosition).getId() == newContent.get(newItemPosition).getId();
            }

            @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return TextUtils.equals(
                        projects.get(oldItemPosition).getName(),
                        newContent.get(newItemPosition).getName())
                        &&
                        TextUtils.equals(
                                projects.get(oldItemPosition).getDescription(),
                                newContent.get(newItemPosition).getDescription())
                        ;
            }
        });

        projects.clear();
        projects.addAll(newContent);
        result.dispatchUpdatesTo(this);
    }

    @Override protected ProjectViewHolder instantiateViewHolder(int viewType, View view) {
        return new ProjectViewHolder(listener, view);
    }

    @Override protected int getLayout(int viewType) {
        return R.layout.item_project;
    }

    @Override protected Project getItem(int position) {
        return projects.get(position);
    }
}