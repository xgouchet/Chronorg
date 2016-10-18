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

    @BindView(R.id.name) TextView name;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.underline) View underline;


    public EntityViewHolder(@NonNull Listener<Entity> listener, View itemView) {
        super(listener, itemView);
        bind(this, itemView);
    }

    @Override public void onBindItem(@NonNull Entity entity) {
        name.setText(entity.getName());

        final String description = entity.getDescription();
        if (TextUtils.isEmpty(description)) {
            this.description.setVisibility(View.GONE);
        } else {
            this.description.setVisibility(View.VISIBLE);
            this.description.setText(description);
        }

        underline.setBackgroundColor(entity.getColor());
    }

    @OnClick(R.id.entity)
    public void onSelectEntity() {
        fireSelected();
    }


}
