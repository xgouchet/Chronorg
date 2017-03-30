package fr.xgouchet.khronorg.feature.editor.viewholders

import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.EditText
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.ui.Cutelry
import fr.xgouchet.khronorg.feature.editor.items.EditorTextItem

/**
 *
 */
class EditorTextViewHolder(view: View)
    : EditorItemViewHolder<EditorTextItem>(null, view), TextWatcher {

    val edit: EditText by Cutelry.knife(R.id.input_text)
    val label: TextView by Cutelry.knife(R.id.input_text_label)

    init {
        edit.addTextChangedListener(this)
    }

    override fun onBindItem(item: EditorTextItem) {
        edit.setText(item.text, TextView.BufferType.EDITABLE)
        label.setText(item.hintRes)
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