package fr.xgouchet.khronorg.feature.portals

import android.view.View
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.data.models.Direction
import fr.xgouchet.khronorg.ui.Cutelry.knife
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import io.reactivex.functions.Consumer
import org.joda.time.ReadableDuration
import org.joda.time.ReadableInterval

/**
 * @author Xavier F. Gouchet
 */
class PortalViewHolder(val formatter: Formatter<ReadableInterval>,

                       listener: Consumer<Portal>?, itemView: View)
    : BaseViewHolder<Portal>(listener, itemView) {


    internal val name: TextView by knife(R.id.name, itemView)
    internal val info: TextView by knife(R.id.info, itemView)
    internal val underline: View by knife(R.id.underline, itemView)

    init {
        if (listener != null) {
            itemView.setOnClickListener({ view -> listener.accept(item) })
        }
    }

    override fun onBindItem(item: Portal) {
        name.text = item.name

        val dir: String
        when (item.direction) {
            Direction.FUTURE -> dir = "→"
            Direction.PAST -> dir = "←"
            else -> dir = "?"
        }

        info.text = "${formatter.format(item.delay)} $dir"

        underline.setBackgroundColor(item.color)
    }
}