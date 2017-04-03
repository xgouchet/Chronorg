package fr.xgouchet.khronorg.feature.timeline

import android.view.View
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.ui.Cutelry
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import org.joda.time.ReadableInstant


/**
 * @author Xavier F. Gouchet
 */
class ShardViewHolder(itemView: View, val instantFormatter: Formatter<ReadableInstant>)
    : BaseViewHolder<TimelineShard>(null, itemView) {

    internal val instant: TextView by Cutelry.knife(R.id.shard_instant, itemView)
    internal val label: TextView by Cutelry.knife(R.id.shard_label, itemView)
    internal val prefix: ShardPrefixView by Cutelry.knife(R.id.shard_prefix, itemView)

    init {
        if (listener != null) {
            itemView.setOnClickListener({ view -> listener.accept(item) })
        }
    }

    override fun onBindItem(item: TimelineShard) {
        label.text = item.label
        instant.text = instantFormatter.format(item.instant)
        prefix.shard = item
    }
}