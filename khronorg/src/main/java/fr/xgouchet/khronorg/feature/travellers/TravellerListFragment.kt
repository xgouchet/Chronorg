package fr.xgouchet.khronorg.feature.travellers

import android.os.Bundle
import android.view.View
import fr.xgouchet.khronorg.commons.formatters.DefaultInstantFormatter
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.feature.travellers.TravellerAdapter
import fr.xgouchet.khronorg.ui.fragments.ListFragment

/**
 * @author Xavier F. Gouchet
 */
class TravellerListFragment : ListFragment<Traveller>(true) {


    override val adapter: BaseAdapter<Traveller> = TravellerAdapter(DefaultInstantFormatter, this)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { e -> presenter.itemCreated() }
    }
}