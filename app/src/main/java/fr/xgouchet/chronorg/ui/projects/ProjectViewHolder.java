package fr.xgouchet.chronorg.ui.projects;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.model.Project;
import fr.xgouchet.chronorg.ui.base.BaseViewHolder;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class ProjectViewHolder extends BaseViewHolder<Project> {

    @BindView(R.id.name) TextView name;
    @BindView(R.id.description) TextView description;

    protected ProjectViewHolder(View itemView) {
        super(itemView);
        bind(this, itemView);
    }

    @Override public void bindItem(@NonNull Project item) {
        name.setText(item.getName());
        final String description = item.getDescription();
        if (TextUtils.isEmpty(description)) {
            this.description.setVisibility(View.GONE);
        } else {
            this.description.setVisibility(View.VISIBLE);
            this.description.setText(description);
        }
    }
}
