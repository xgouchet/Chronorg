package fr.xgouchet.khronorg.feature.jumps

import android.os.Bundle
import android.view.View
import fr.xgouchet.khronorg.commons.formatters.DefaultInstantFormatter
import fr.xgouchet.khronorg.feature.jumps.Jump
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.feature.jumps.JumpAdapter
import fr.xgouchet.khronorg.feature.travellers.TravellerAdapter
import fr.xgouchet.khronorg.ui.fragments.ListFragment

/**
 * @author Xavier F. Gouchet
 */
class JumpListFragment : ListFragment<Jump>(true) {


    override val adapter: BaseAdapter<Jump> = JumpAdapter(DefaultInstantFormatter, this)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { e -> presenter.itemSelected(null) }
    }
}