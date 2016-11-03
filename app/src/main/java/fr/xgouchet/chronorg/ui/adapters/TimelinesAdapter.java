package fr.xgouchet.chronorg.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;

import org.joda.time.ReadablePeriod;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.formatters.Formatter;
import fr.xgouchet.chronorg.data.models.Timeline;
import fr.xgouchet.chronorg.ui.viewholders.BaseViewHolder;
import fr.xgouchet.chronorg.ui.viewholders.TimelineViewHolder;

/**
 * @author Xavier Gouchet
 */
public class TimelinesAdapter extends BaseSimpleAdapter<Timeline, TimelineViewHolder> {


    @NonNull private final BaseViewHolder.Listener<Timeline> listener;
    @NonNull private final Formatter<ReadablePeriod> formatter;

    public TimelinesAdapter(@NonNull BaseViewHolder.Listener<Timeline> listener,
                            @NonNull Formatter<ReadablePeriod> formatter) {
        this.listener = listener;
        this.formatter = formatter;
    }

    @Override protected TimelineViewHolder instantiateViewHolder(int viewType, View view) {
        return new TimelineViewHolder(listener, view, formatter);
    }

    @Override protected int getLayout(int viewType) {
        return R.layout.item_base;
    }

}
