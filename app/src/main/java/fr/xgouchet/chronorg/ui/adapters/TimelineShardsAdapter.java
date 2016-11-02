package fr.xgouchet.chronorg.ui.adapters;

import android.view.View;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Shard;
import fr.xgouchet.chronorg.ui.viewholders.TimelineShardViewHolder;

/**
 * @author Xavier Gouchet
 */
public class TimelineShardsAdapter extends BaseSimpleAdapter<Shard, TimelineShardViewHolder> {


    public TimelineShardsAdapter() {
    }

    @Override protected TimelineShardViewHolder instantiateViewHolder(int viewType, View view) {
        return new TimelineShardViewHolder(null, view);
    }

    @Override protected int getLayout(int viewType) {
        return R.layout.item_shard;
    }

}
