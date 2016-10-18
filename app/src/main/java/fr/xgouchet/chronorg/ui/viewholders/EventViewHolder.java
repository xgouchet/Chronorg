package fr.xgouchet.chronorg.ui.viewholders;

import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Event;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class EventViewHolder extends BaseViewHolder<Event> {

    public interface Listener {

        void onEventSelected(@NonNull Event entity);
    }

    @BindView(R.id.name) TextView name;
    @BindView(R.id.description) TextView description;
    @BindView(R.id.underline) View underline;

    private Event event;

    @NonNull private final Listener listener;

    public EventViewHolder(@NonNull Listener listener, View itemView) {
        super(itemView);
        this.listener = listener;
        bind(this, itemView);
    }

    @Override public void bindItem(@NonNull Event event) {
        this.event = event;

        name.setText(event.getName());

        final String description = event.getDescription();
        if (TextUtils.isEmpty(description)) {
            this.description.setVisibility(View.GONE);
        } else {
            this.description.setVisibility(View.VISIBLE);
            this.description.setText(description);
        }

        underline.setBackgroundColor(event.getColor());
    }

    @OnClick(R.id.entity)
    public void onSelectEntity() {
        if (event != null) {
            listener.onEventSelected(event);
        }
    }
}
