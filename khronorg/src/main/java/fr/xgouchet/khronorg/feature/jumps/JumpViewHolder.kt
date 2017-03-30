package fr.xgouchet.khronorg.feature.jumps

import android.view.View
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.feature.jumps.Jump
import fr.xgouchet.khronorg.ui.Cutelry.knife
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import io.reactivex.functions.Consumer
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
class JumpViewHolder(val formatter: Formatter<ReadableInstant>, listener: Consumer<Jump>?, itemView: View)
    : BaseViewHolder<Jump>(listener, itemView) {


    internal val from: TextView by knife(R.id.from_instant, itemView)
    internal val to: TextView by knife(R.id.to_instant, itemView)

    init {
        if (listener != null) {
            itemView.setOnClickListener({ view -> listener.accept(item) })
        }
    }

    override fun onBindItem(item: Jump) {
        from.text = formatter.format(item.from)
        to.text = formatter.format(item.destination)
    }
}