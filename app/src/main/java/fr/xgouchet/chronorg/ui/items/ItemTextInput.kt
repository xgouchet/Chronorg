package fr.xgouchet.chronorg.ui.items

import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.ui.source.TextSource

class ItemTextInput {

    // region ViewModel

    data class ViewModel(
            private val index: Item.Index = Item.Index(0, 0),
            val hint: TextSource? = null,
            val value: TextSource? = null,
            val data: Any? = null
    ) : Item.ViewModel() {

        override fun type(): Item.Type = Item.Type.TEXT_INPUT

        override fun index(): Item.Index = index

        override fun data(): Any? = data

    }

    // endregion

    // region ViewHolder

    class ViewHolder(
            itemView: View,
            private val listener: (Item.Event) -> Unit
    ) : Item.ViewHolder<ViewModel>(itemView) {

        private val inputLayout: TextInputLayout = itemView.findViewById(R.id.input_layout)
        private val inputField: TextInputEditText = itemView.findViewById(R.id.input_field)

        val textChangedListener = object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                onValueChanged(s?.toString())
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        }

        override fun onBind(item: ViewModel) {
            inputField.removeTextChangedListener(textChangedListener)
            item.hint?.setHint(inputField)
            item.value?.setText(inputField)
            inputField.addTextChangedListener(textChangedListener)
        }

        private fun onValueChanged(updatedValue : String?){
            val event = Item.Event(boundItem, Item.Action.VALUE_CHANGED, updatedValue)
            listener(event)
        }
    }

    // endregion

    // region ViewHolderInflater

    class ViewHolderInflater
        : Item.ViewHolderInflater<ViewHolder> {
        override fun inflate(inflater: LayoutInflater,
                             parent: ViewGroup,
                             listener: (Item.Event) -> Unit): ViewHolder {
            val view = inflater.inflate(R.layout.item_text_input, parent, false)
            return ViewHolder(view, listener)
        }
    }

    // endregion

}
