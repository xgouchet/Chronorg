package fr.xgouchet.khronorg.feature.portals

import android.os.Bundle
import android.view.View
import fr.xgouchet.khronorg.commons.formatters.DefaultIntervalFormatter
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.fragments.ListFragment

/**
 * @author Xavier F. Gouchet
 */
class PortalListFragment : ListFragment<Portal>(true) {


    override val adapter: BaseAdapter<Portal> = PortalAdapter(DefaultIntervalFormatter, this)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { e -> presenter.itemSelected(null) }
    }
}