package fr.xgouchet.khronorg.feature.events

import android.view.View
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.feature.events.EventViewHolder
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import fr.xgouchet.khronorg.feature.travellers.TravellerViewHolder
import io.reactivex.functions.Consumer
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
class EventAdapter(val formatter: Formatter<ReadableInstant>, val listener: Consumer<Event>) : BaseAdapter<Event>() {

    override val layoutId: Int = R.layout.item_traveller

    override fun instantiateViewHolder(viewType: Int, view: View): BaseViewHolder<Event> {
        return EventViewHolder(formatter, listener, view)
    }
}