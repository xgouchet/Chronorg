package fr.xgouchet.khronorg.feature.travellers

import android.view.View
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import fr.xgouchet.khronorg.feature.travellers.TravellerViewHolder
import io.reactivex.functions.Consumer
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
class TravellerAdapter(val formatter: Formatter<ReadableInstant>, val listener: Consumer<Traveller>) : BaseAdapter<Traveller>() {

    override val layoutId: Int = R.layout.item_traveller

    override fun instantiateViewHolder(viewType: Int, view: View): BaseViewHolder<Traveller> {
        return TravellerViewHolder(formatter, listener, view)
    }
}