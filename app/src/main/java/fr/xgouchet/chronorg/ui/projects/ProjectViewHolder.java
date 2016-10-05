package fr.xgouchet.chronorg.ui.projects;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.model.Project;
import fr.xgouchet.chronorg.ui.base.BaseViewHolder;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class ProjectViewHolder extends BaseViewHolder<Project> {

    public static interface Listener {
        void onProjectSelected(@NonNull Project project);
    }

    @BindView(R.id.name) TextView name;
    @BindView(R.id.description) TextView description;

    private Project project;

    @NonNull private final Listener listener;

    protected ProjectViewHolder(@NonNull Listener listener, View itemView) {
        super(itemView);
        this.listener = listener;
        bind(this, itemView);
    }

    @Override public void bindItem(@NonNull Project item) {
        project = item;
        name.setText(item.getName());
        final String description = item.getDescription();
        if (TextUtils.isEmpty(description)) {
            this.description.setVisibility(View.GONE);
        } else {
            this.description.setVisibility(View.VISIBLE);
            this.description.setText(description);
        }
    }

    @OnClick(R.id.project)
    public void onSelectProject() {
        if (project != null) {
            listener.onProjectSelected(project);
        }
    }
}
