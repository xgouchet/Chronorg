package fr.xgouchet.chronorg.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.ui.viewholders.EventViewHolder;

/**
 * @author Xavier Gouchet
 */
public class EventsAdapter extends BaseSimpleAdapter<Event, EventViewHolder> {


    @NonNull /*package*/ final List<Event> events;
    @NonNull private final EventViewHolder.Listener listener;

    public EventsAdapter(@NonNull List<Event> events, @NonNull EventViewHolder.Listener listener) {
        this.events = events;
        this.listener = listener;
    }

    @Override public int getItemCount() {
        return events.size();
    }

    @Override public void update(final List<Event> newContent) {
        events.clear();
        events.addAll(newContent);
        notifyDataSetChanged();
    }

    @Override protected EventViewHolder instantiateViewHolder(int viewType, View view) {
        return new EventViewHolder(listener, view);
    }

    @Override protected int getLayout(int viewType) {
        return R.layout.item_shard;
    }

    @Override protected Event getItem(int position) {
        return events.get(position);
    }
}
