package fr.xgouchet.khronorg.feature.timeline

import fr.xgouchet.khronorg.commons.formatters.ShortInstantFormatter
import fr.xgouchet.khronorg.commons.formatters.TimelineInstantFormatter
import fr.xgouchet.khronorg.feature.portals.Portal
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.fragments.ListFragment

/**
 * @author Xavier F. Gouchet
 */
class ShardListFragment : ListFragment<TimelineShard>(false) {


    override val adapter: BaseAdapter<TimelineShard> = ShardAdapter(TimelineInstantFormatter)


    fun setPortals(portals : List<Portal>) {
        (adapter as ShardAdapter).setPortals(portals)
    }
}