package fr.xgouchet.chronorg.ui.viewholders;

import android.support.annotation.NonNull;
import android.view.View;

import org.joda.time.ReadablePeriod;

import fr.xgouchet.chronorg.data.formatters.Formatter;
import fr.xgouchet.chronorg.data.models.Portal;

/**
 * @author Xavier Gouchet
 */
public class PortalViewHolder extends BaseProjectItemViewHolder<Portal> {

    @NonNull final Formatter<ReadablePeriod> formatter;

    public PortalViewHolder(@NonNull Listener<Portal> listener,
                            View itemView,
                            @NonNull Formatter<ReadablePeriod> formatter) {
        super(listener, itemView);
        this.formatter = formatter;
    }


    @Override protected String getName(@NonNull Portal portal) {
        return portal.getName();
    }

    @Override protected int getColor(@NonNull Portal portal) {
        return portal.getColor();
    }

    @Override protected String getInfo(@NonNull Portal portal) {
        String dir = "";
        switch (portal.getDirection()) {
            case Portal.Direction.BOTH:
                dir = " ↔";
                break;
            case Portal.Direction.FUTURE:
                dir = " →";
                break;
            case Portal.Direction.PAST:
                dir = " ←";
                break;
        }
        return formatter.format(portal.getDelay().toPeriod()) + dir;
    }
}
