package fr.xgouchet.chronorg.ui.fragments;

import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Shard;
import fr.xgouchet.chronorg.ui.adapters.TimelineShardsAdapter;

/**
 * @author Xavier Gouchet
 */
public class ShardListFragment extends BaseListFragment<Shard, TimelineShardsAdapter> {


    public static ShardListFragment createFragment() {
        return new ShardListFragment();
    }

    @Override protected TimelineShardsAdapter getAdapter() {
        return new TimelineShardsAdapter();
    }

    @Override public void showCreateItemUi() {
    }

    @Override public void showItem(@NonNull Shard item) {
    }

}
