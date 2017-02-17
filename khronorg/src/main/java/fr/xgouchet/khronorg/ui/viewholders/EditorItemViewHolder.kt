package fr.xgouchet.khronorg.ui.viewholders

import android.text.Editable
import android.text.TextWatcher
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

class EditorTextViewHolder(view: View) : EditorItemViewHolder<EditorTextItem>(view), TextWatcher {

    var item: EditorTextItem? = null

    val label: TextView by knife(R.id.item_label)
    val edit: EditText by knife(R.id.item_text)

    init {
        edit.addTextChangedListener(this)
    }

    override fun preformBind(typedItem: EditorTextItem) {
        item = typedItem
        edit.setText(typedItem.text, TextView.BufferType.EDITABLE)
        label.setText(typedItem.hintRes)
    }

    override fun afterTextChanged(s: Editable?) {
        item?.let {
            it.text = s.toString()
        }
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }
}