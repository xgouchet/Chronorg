package fr.xgouchet.khronorg.feature.editor

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.feature.editor.items.EditorColorItem
import fr.xgouchet.khronorg.feature.editor.items.EditorInstantItem
import fr.xgouchet.khronorg.feature.editor.items.EditorItem
import fr.xgouchet.khronorg.feature.editor.items.EditorItem.Companion.ITEM_COLOR
import fr.xgouchet.khronorg.feature.editor.items.EditorItem.Companion.ITEM_INSTANT
import fr.xgouchet.khronorg.feature.editor.items.EditorItem.Companion.ITEM_TEXT
import fr.xgouchet.khronorg.feature.editor.viewholders.EditorColorViewHolder
import fr.xgouchet.khronorg.feature.editor.viewholders.EditorEmptyViewHolder
import fr.xgouchet.khronorg.feature.editor.viewholders.EditorInstantViewHolder
import fr.xgouchet.khronorg.feature.editor.viewholders.EditorTextViewHolder
import fr.xgouchet.khronorg.ui.viewholders.*
import io.reactivex.functions.Consumer
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
class EditorAdapter(val formatter: Formatter<ReadableInstant>, val listener: EditorAdapterListener) : BaseAdapter<EditorItem>() {

    override val layoutId: Int = R.layout.item_project

    override fun getItemViewType(position: Int): Int {
        return content[position].itemType
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<EditorItem> {
        val layoutId = getlayoutId(viewType)

        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

        return instantiateViewHolder(viewType, view)
    }

    private fun getlayoutId(viewType: Int): Int {
        when (viewType) {
            ITEM_TEXT -> return R.layout.item_edit_text
            ITEM_COLOR -> return R.layout.item_edit_color
            ITEM_INSTANT -> return R.layout.item_edit_instant
            else -> return R.layout.item_edit_empty
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun instantiateViewHolder(viewType: Int, view: View): BaseViewHolder<EditorItem> {
        val viewHolder: Any
        when (viewType) {
            ITEM_TEXT -> viewHolder = EditorTextViewHolder(view)
            ITEM_COLOR -> {
                val consumer = Consumer<EditorColorItem> { colorItem -> listener.pickColor(colorItem) }
                viewHolder = EditorColorViewHolder(consumer, view)
            }
            ITEM_INSTANT -> {
                val consumer = Consumer<EditorInstantItem> { instantItem -> listener.pickInstant(instantItem) }
                viewHolder = EditorInstantViewHolder(formatter, consumer, view)
            }
            else -> viewHolder = EditorEmptyViewHolder(view)
        }
        return viewHolder as BaseViewHolder<EditorItem>
    }


}