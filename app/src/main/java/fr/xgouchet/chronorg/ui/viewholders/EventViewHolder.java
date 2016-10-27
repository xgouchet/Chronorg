package fr.xgouchet.chronorg.ui.viewholders;

import android.support.annotation.NonNull;
import android.view.View;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import fr.xgouchet.chronorg.data.models.Event;

/**
 * @author Xavier Gouchet
 */
public class EventViewHolder extends BaseProjectItemViewHolder<Event> {
    final DateTimeFormatter dtf = DateTimeFormat.forStyle("MS").withZoneUTC();

    public EventViewHolder(@NonNull Listener<Event> listener, View itemView) {
        super(listener, itemView);
    }

    @Override protected String getName(@NonNull Event item) {
        return item.getName();
    }

    @Override protected int getColor(@NonNull Event item) {
        return item.getColor();
    }

    @Override protected String getInfo(@NonNull Event item) {
        return dtf.print(item.getInstant());
    }
}
