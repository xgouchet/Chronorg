package fr.xgouchet.khronorg.ui.fragments

import android.os.Bundle
import android.view.View
import fr.xgouchet.khronorg.data.formatters.InstantFormatter
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.adapters.TravellerAdapter

/**
 * @author Xavier F. Gouchet
 */
class TravellerListFragment : ListFragment<Traveller>() {


    override val adapter: BaseAdapter<Traveller> = TravellerAdapter(InstantFormatter, this)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { e -> presenter.itemSelected(null) }
    }
}