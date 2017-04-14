package fr.xgouchet.khronorg.feature.timeline

import android.view.View
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.feature.portals.Portal
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import org.joda.time.ReadableInstant
import java.util.*

/**
 * @author Xavier F. Gouchet
 */
class ShardAdapter(val instantFormatter: Formatter<ReadableInstant>) : BaseAdapter<TimelineShard>() {

    companion object {
        val MASK_YEAR: Int = 0x10000
    }

    override val layoutId: Int = R.layout.item_shard
    val portals: MutableList<Portal> = ArrayList()

    override fun getLayoutId(viewType: Int): Int {
        if ((viewType and MASK_YEAR) == MASK_YEAR) {
            return R.layout.item_shard_year
        } else {
            return layoutId
        }
    }

    override fun getItemViewType(position: Int): Int {

        val item = content[position]
        when (item.type) {
            TimelineShard.ShardType.YEAR -> return (item.prefix.size or MASK_YEAR)
            else -> return item.prefix.size
        }

    }

    override fun instantiateViewHolder(viewType: Int, view: View): BaseViewHolder<TimelineShard> {
        if ((viewType and MASK_YEAR) == MASK_YEAR) {
            return ShardYearViewHolder(view)
        } else {
            return ShardViewHolder(view, instantFormatter, portals)
        }
    }

    fun setPortals(list: List<Portal>) {
        portals.clear()
        portals.addAll(list)
        notifyDataSetChanged()
    }
}