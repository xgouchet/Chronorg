package fr.xgouchet.khronorg.feature.portals

import android.view.View
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import io.reactivex.functions.Consumer
import org.joda.time.ReadableInterval

/**
 * @author Xavier F. Gouchet
 */
class PortalAdapter(val formatter: Formatter<ReadableInterval>, val listener: Consumer<Portal>) : BaseAdapter<Portal>() {

    override val layoutId: Int = R.layout.item_portal

    override fun instantiateViewHolder(viewType: Int, view: View): BaseViewHolder<Portal> {
        return PortalViewHolder(formatter, listener, view)
    }
}