package fr.xgouchet.khronorg.ui.viewholders

import android.view.View
import android.widget.EditText
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.ui.Cutelry.knife
import fr.xgouchet.khronorg.ui.editor.EditorItem
import fr.xgouchet.khronorg.ui.editor.EditorTextItem

/**
 * @author Xavier F. Gouchet
 */
abstract class EditorItemViewHolder<T : EditorItem>(itemView: View) : BaseViewHolder<EditorItem>(null, itemView) {

    override final fun onBindItem(item: EditorItem) {
        val typedItem = item as T
        preformBind(typedItem)
    }

    abstract fun preformBind(typedItem: T)
}

class EditorEmptyViewHolder(itemView: View) : EditorItemViewHolder<EditorItem>(itemView) {
    override fun preformBind(typedItem: EditorItem) {
    }
}

class EditorTextViewHolder(view: View) : EditorItemViewHolder<EditorTextItem>(view) {

    val edit: EditText by knife(R.id.item_text)

    override fun preformBind(typedItem: EditorTextItem) {
        edit.setText(typedItem.text, TextView.BufferType.EDITABLE)
    }
}