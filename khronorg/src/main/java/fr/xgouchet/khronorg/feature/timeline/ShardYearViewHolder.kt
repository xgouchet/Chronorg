package fr.xgouchet.khronorg.feature.timeline

import android.view.View
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.ui.Cutelry
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder


/**
 * @author Xavier F. Gouchet
 */
class ShardYearViewHolder(itemView: View)
    : BaseViewHolder<TimelineShard>(null, itemView) {

    internal val label: TextView by Cutelry.knife(R.id.shard_label, itemView)
    internal val prefix: ShardPrefixView by Cutelry.knife(R.id.shard_prefix, itemView)

    override fun onBindItem(item: TimelineShard) {
        label.text = item.label
        prefix.shard = item
    }
}