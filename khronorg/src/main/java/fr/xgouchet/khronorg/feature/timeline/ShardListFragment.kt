package fr.xgouchet.khronorg.feature.timeline

import fr.xgouchet.khronorg.commons.formatters.DefaultInstantFormatter
import fr.xgouchet.khronorg.feature.timeline.TimelineShard
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.feature.timeline.ShardAdapter
import fr.xgouchet.khronorg.ui.fragments.ListFragment

/**
 * @author Xavier F. Gouchet
 */
class ShardListFragment : ListFragment<TimelineShard>(false) {

    override val adapter: BaseAdapter<TimelineShard> = ShardAdapter(DefaultInstantFormatter)
}