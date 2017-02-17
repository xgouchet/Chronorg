package fr.xgouchet.khronorg.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.ui.editor.EditorItem
import fr.xgouchet.khronorg.ui.editor.EditorItem.Companion.ITEM_TEXT
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import fr.xgouchet.khronorg.ui.viewholders.EditorEmptyViewHolder
import fr.xgouchet.khronorg.ui.viewholders.EditorTextViewHolder

/**
 * @author Xavier F. Gouchet
 */
class EditorAdapter : BaseAdapter<EditorItem>() {

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
            else -> return R.layout.item_edit_empty
        }
    }

    override fun instantiateViewHolder(viewType: Int, view: View): BaseViewHolder<EditorItem> {
        when (viewType) {
            ITEM_TEXT -> return EditorTextViewHolder(view)
            else -> return EditorEmptyViewHolder(view)
        }
    }


}