package fr.xgouchet.khronorg.ui.fragments

import android.os.Bundle
import android.view.View
import fr.xgouchet.khronorg.data.formatters.DefaultInstantFormatter
import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.adapters.JumpAdapter
import fr.xgouchet.khronorg.ui.adapters.TravellerAdapter

/**
 * @author Xavier F. Gouchet
 */
class JumpListFragment : ListFragment<Jump>() {


    override val adapter: BaseAdapter<Jump> = JumpAdapter(DefaultInstantFormatter, this)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { e -> presenter.itemSelected(null) }
    }
}