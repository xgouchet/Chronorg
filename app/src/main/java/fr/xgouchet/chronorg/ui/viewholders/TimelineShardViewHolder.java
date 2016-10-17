package fr.xgouchet.chronorg.ui.viewholders;

import android.support.annotation.NonNull;
import android.view.View;
import android.widget.TextView;

import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import butterknife.BindView;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.TimelineShard;
import fr.xgouchet.chronorg.ui.TimelineShardView;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class TimelineShardViewHolder extends BaseViewHolder<TimelineShard> {


    final DateTimeFormatter dtf = DateTimeFormat.forStyle("MM");

    @BindView(R.id.legend) TextView legend;
    @BindView(R.id.instant) TextView instant;
    @BindView(R.id.timeline) TimelineShardView timelineShard;


    public TimelineShardViewHolder(View itemView) {
        super(itemView);
        bind(this, itemView);
    }

    @Override public void bindItem(@NonNull TimelineShard shard) {

        legend.setText(shard.getLegend());
        instant.setText(dtf.print(shard.getInstant()));
        timelineShard.setTimelineShard(shard);

    }

}
