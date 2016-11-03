package fr.xgouchet.chronorg.ui.viewholders;

import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.View;

import org.joda.time.ReadablePeriod;

import fr.xgouchet.chronorg.data.formatters.Formatter;
import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.data.models.Timeline;

/**
 * @author Xavier Gouchet
 */
public class TimelineViewHolder extends BaseProjectItemViewHolder<Timeline> {

    @NonNull final Formatter<ReadablePeriod> formatter;

    public TimelineViewHolder(@NonNull Listener<Timeline> listener,
                              View itemView,
                              @NonNull Formatter<ReadablePeriod> formatter) {
        super(listener, itemView);
        this.formatter = formatter;
    }


    @Override protected String getName(@NonNull Timeline timeline) {
        return timeline.getName();
    }

    @Override protected int getColor(@NonNull Timeline timeline) {
        return Color.TRANSPARENT;
    }

    @Override protected String getInfo(@NonNull Timeline timeline) {
        String info = "";
        final Portal portal = timeline.getPortal();
        final Timeline parent = timeline.getParent();

        if ((portal != null) && (parent != null)) {


            switch (timeline.getDirection()) {
                case Portal.Direction.FUTURE:
                    info = parent.getName() + " → " + formatter.format(portal.getDelay().toPeriod()) + " (" + portal.getName() + ")";
                    break;
                case Portal.Direction.PAST:
                    info = parent.getName() + " ← " + formatter.format(portal.getDelay().toPeriod()) + " (" + portal.getName() + ")";
                    break;
            }
        }
        return info;
    }
}
