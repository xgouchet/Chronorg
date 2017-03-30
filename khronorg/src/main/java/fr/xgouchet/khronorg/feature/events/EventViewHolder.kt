package fr.xgouchet.khronorg.feature.events

import android.view.View
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.ui.Cutelry.knife
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import io.reactivex.functions.Consumer
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
class EventViewHolder(val formatter: Formatter<ReadableInstant>, listener: Consumer<Event>?, itemView: View)
    : BaseViewHolder<Event>(listener, itemView) {


    internal val name: TextView by knife(R.id.name, itemView)
    internal val info: TextView by knife(R.id.info, itemView)
    internal val underline: View by knife(R.id.underline, itemView)

    init {
        if (listener != null) {
            itemView.setOnClickListener({ v -> listener.accept(item) })
        }
    }

    override fun onBindItem(item: Event) {
        name.text = item.name

        info.text = formatter.format(item.instant)

        underline.setBackgroundColor(item.color)
    }
}