package fr.xgouchet.khronorg.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.ui.editor.EditorColorItem
import fr.xgouchet.khronorg.ui.editor.EditorItem
import fr.xgouchet.khronorg.ui.editor.EditorItem.Companion.ITEM_COLOR
import fr.xgouchet.khronorg.ui.editor.EditorItem.Companion.ITEM_TEXT
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import fr.xgouchet.khronorg.ui.viewholders.EditorColorViewHolder
import fr.xgouchet.khronorg.ui.viewholders.EditorEmptyViewHolder
import fr.xgouchet.khronorg.ui.viewholders.EditorTextViewHolder
import io.reactivex.functions.Consumer

/**
 * @author Xavier F. Gouchet
 */
class EditorAdapter(val listener: EditorAdapterListener) : BaseAdapter<EditorItem>() {

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
            else -> viewHolder = EditorEmptyViewHolder(view)
        }
        return viewHolder as BaseViewHolder<EditorItem>
    }


}