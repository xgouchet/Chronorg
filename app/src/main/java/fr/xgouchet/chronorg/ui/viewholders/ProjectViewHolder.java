package fr.xgouchet.chronorg.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Project;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class ProjectViewHolder extends BaseViewHolder<Project> {


    @BindView(R.id.name) TextView name;
    @BindView(R.id.description) TextView description;

    public ProjectViewHolder(@Nullable Listener<Project> listener,
                             @NonNull View itemView) {
        super(listener, itemView);
        bind(this, itemView);
    }

    @Override public void onBindItem(@NonNull Project project) {
        name.setText(project.getName());
        final String description = project.getDescription();
        if (TextUtils.isEmpty(description)) {
            this.description.setVisibility(View.GONE);
        } else {
            this.description.setVisibility(View.VISIBLE);
            this.description.setText(description);
        }
    }

    @OnClick(R.id.project)
    public void onSelectProject() {
        fireSelected();
    }
}
