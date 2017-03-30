package fr.xgouchet.khronorg.feature.jumps

import android.view.View
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.feature.jumps.Jump
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import fr.xgouchet.khronorg.feature.jumps.JumpViewHolder
import fr.xgouchet.khronorg.feature.travellers.TravellerViewHolder
import io.reactivex.functions.Consumer
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
class JumpAdapter(val formatter: Formatter<ReadableInstant>, val listener: Consumer<Jump>) : BaseAdapter<Jump>() {

    override val layoutId: Int = R.layout.item_jump

    override fun instantiateViewHolder(viewType: Int, view: View): BaseViewHolder<Jump> {
        return JumpViewHolder(formatter, listener, view)
    }
}