package fr.xgouchet.khronorg.ui.adapters

import android.view.View
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.formatters.Formatter
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import fr.xgouchet.khronorg.ui.viewholders.TravellerViewHolder
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