package fr.xgouchet.khronorg.ui.viewholders

import android.view.View
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.formatters.Formatter
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.ui.Cutelry.knife
import io.reactivex.functions.Consumer
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
class TravellerViewHolder(val formatter: Formatter<ReadableInstant>, listener: Consumer<Traveller>, itemView: View)
    : BaseViewHolder<Traveller>(listener, itemView) {


    internal val name: TextView by knife(R.id.name, itemView)
    internal val info: TextView by knife(R.id.info, itemView)
    internal val underline: View by knife(R.id.underline, itemView)

    override fun onBindItem(item: Traveller) {
        name.text = item.name

        info.text = StringBuilder()
                .append("* ")
                .append(formatter.format(item.birth))
                .append("\n† ")
                .append(formatter.format(item.death))

        underline.setBackgroundColor(item.color)
    }
}