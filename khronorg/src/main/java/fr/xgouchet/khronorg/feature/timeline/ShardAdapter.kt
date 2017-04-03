package fr.xgouchet.khronorg.feature.timeline

import android.view.View
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.feature.timeline.TimelineShard
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import fr.xgouchet.khronorg.feature.timeline.ShardViewHolder
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
class ShardAdapter(val instantFormatter : Formatter<ReadableInstant>) : BaseAdapter<TimelineShard>() {

    override val layoutId: Int = R.layout.item_shard

    override fun instantiateViewHolder(viewType: Int, view: View): BaseViewHolder<TimelineShard> {
        return ShardViewHolder(view, instantFormatter)
    }
}