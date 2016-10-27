package fr.xgouchet.chronorg.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.ui.viewholders.BaseViewHolder;
import fr.xgouchet.chronorg.ui.viewholders.EventViewHolder;

/**
 * @author Xavier Gouchet
 */
public class EventsAdapter extends BaseSimpleAdapter<Event, EventViewHolder> {


    @NonNull private final BaseViewHolder.Listener<Event> listener;

    public EventsAdapter(@NonNull BaseViewHolder.Listener<Event> listener) {
        this.listener = listener;
    }

    @Override protected EventViewHolder instantiateViewHolder(int viewType, View view) {
        return new EventViewHolder(listener, view);
    }

    @Override protected int getLayout(int viewType) {
        return R.layout.item_base;
    }

}
