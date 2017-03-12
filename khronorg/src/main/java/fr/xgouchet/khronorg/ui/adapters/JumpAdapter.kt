package fr.xgouchet.khronorg.ui.adapters

import android.view.View
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.formatters.Formatter
import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import fr.xgouchet.khronorg.ui.viewholders.JumpViewHolder
import fr.xgouchet.khronorg.ui.viewholders.TravellerViewHolder
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