package fr.xgouchet.chronorg.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;

import java.util.List;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.TimelineShard;
import fr.xgouchet.chronorg.ui.viewholders.TimelineShardViewHolder;

/**
 * @author Xavier Gouchet
 */
public class TimelineShardsAdapter extends BaseSimpleAdapter<TimelineShard, TimelineShardViewHolder> {


    @NonNull /*package*/ final List<TimelineShard> shards;

    public TimelineShardsAdapter(@NonNull List<TimelineShard> shards) {
        this.shards = shards;
    }

    @Override public int getItemCount() {
        return shards.size();
    }

    @Override public void update(final List<TimelineShard> newContent) {
        shards.clear();
        shards.addAll(newContent);
        notifyDataSetChanged();
    }

    @Override protected TimelineShardViewHolder instantiateViewHolder(int viewType, View view) {
        return new TimelineShardViewHolder(null, view);
    }

    @Override protected int getLayout(int viewType) {
        return R.layout.item_shard;
    }

    @Override protected TimelineShard getItem(int position) {
        return shards.get(position);
    }
}
