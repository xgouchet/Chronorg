package fr.xgouchet.khronorg.feature.timeline

import android.view.View
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.data.models.Direction
import fr.xgouchet.khronorg.feature.portals.Portal
import fr.xgouchet.khronorg.ui.Cutelry
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import org.joda.time.ReadableInstant


/**
 * @author Xavier F. Gouchet
 */
class ShardViewHolder(itemView: View,
                      val instantFormatter: Formatter<ReadableInstant>,
                      val portals: List<Portal>)
    : BaseViewHolder<TimelineShard>(null, itemView) {

    internal val instants: TextView by Cutelry.knife(R.id.shard_instant, itemView)
    internal val label: TextView by Cutelry.knife(R.id.shard_label, itemView)
    internal val prefix: ShardPrefixView by Cutelry.knife(R.id.shard_prefix, itemView)

    init {
        if (listener != null) {
            itemView.setOnClickListener({ view -> listener.accept(item) })
        }
    }

    override fun onBindItem(item: TimelineShard) {
        label.text = item.label
        prefix.shard = item

        val builder = StringBuilder()
        builder.append(instantFormatter.format(item.instant))

        for ((id, projectId, name, delay, direction) in portals) {
            builder.append("\n")
            val toDuration = delay.toDuration()
            when (direction) {
                Direction.FUTURE -> builder.append(instantFormatter.format(item.instant.toInstant().plus(toDuration)))
                Direction.PAST -> builder.append(instantFormatter.format(item.instant.toInstant().minus(toDuration)))
            }
            builder.append(" ($name)")
        }
        instants.text = builder.toString()
    }
}