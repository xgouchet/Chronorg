package fr.xgouchet.khronorg.feature.events

import android.os.Bundle
import android.view.View
import fr.xgouchet.khronorg.commons.formatters.DefaultInstantFormatter
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.feature.events.EventAdapter
import fr.xgouchet.khronorg.ui.fragments.ListFragment

/**
 * @author Xavier F. Gouchet
 */
class EventListFragment : ListFragment<Event>(true) {


    override val adapter: BaseAdapter<Event> = EventAdapter(DefaultInstantFormatter, this)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { e -> presenter.itemSelected(null) }
    }
}