package fr.xgouchet.khronorg.ui.viewholders

import android.view.View
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.formatters.Formatter
import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.ui.Cutelry.knife
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
            itemView.setOnClickListener({ _ -> listener.accept(item) })
        }
    }

    override fun onBindItem(item: Jump) {
        from.text = formatter.format(item.from)
        to.text = formatter.format(item.destination)
    }
}