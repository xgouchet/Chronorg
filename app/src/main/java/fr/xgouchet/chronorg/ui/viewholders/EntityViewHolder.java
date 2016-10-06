package fr.xgouchet.chronorg.ui.viewholders;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Entity;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class EntityViewHolder extends BaseViewHolder<Entity> {

    public static interface Listener {

        void onEntitySelected(@NonNull Entity project);
    }

    @BindView(R.id.name) TextView name;
    @BindView(R.id.description) TextView description;

    private Entity entity;

    @NonNull private final Listener listener;

    protected EntityViewHolder(@NonNull Listener listener, View itemView) {
        super(itemView);
        this.listener = listener;
        bind(this, itemView);
    }

    @Override public void bindItem(@NonNull Entity entity) {
        this.entity = entity;
        name.setText(entity.getName());
        final String description = entity.getDescription();
        if (TextUtils.isEmpty(description)) {
            this.description.setVisibility(View.GONE);
        } else {
            this.description.setVisibility(View.VISIBLE);
            this.description.setText(description);
        }
    }

    @OnClick(R.id.project)
    public void onSelectProject() {
        if (entity != null) {
            listener.onEntitySelected(entity);
        }
    }
}
