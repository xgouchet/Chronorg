package fr.xgouchet.chronorg.ui.viewholders;

import android.support.annotation.NonNull;
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


    @BindView(R.id.name) TextView name;
    @BindView(R.id.underline) View underline;

    public EventViewHolder(@NonNull Listener<Event> listener, View itemView) {
        super(listener, itemView);
        bind(this, itemView);
    }

    @Override public void onBindItem(@NonNull Event event) {
        name.setText(event.getName());

        underline.setBackgroundColor(event.getColor());
    }

    @OnClick(R.id.event)
    public void onSelectEvent() {
        fireSelected();
    }
}
