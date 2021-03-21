package fr.xgouchet.chronorg.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.ui.source.TextSource

class ItemInstantInput {

    // region ViewModel

    data class ViewModel(
        private val index: Item.Index = Item.Index(0, 0),
        val value: TextSource,
        val data: Any? = null
    ) : Item.ViewModel() {

        override fun type(): Item.Type = Item.Type.INSTANT_INPUT

        override fun index(): Item.Index = index

        override fun data(): Any? = data
    }

    // endregion

    // region ViewHolder

    class ViewHolder(
        itemView: View,
        private val listener: (Item.Event) -> Unit
    ) : Item.ViewHolder<ViewModel>(itemView) {

        private val inputView: TextView = itemView.findViewById(R.id.input_view)

        init {
            inputView.setOnClickListener {
                listener(
                    Item.Event(
                        boundItem,
                        Item.Action.ITEM_TAPPED,
                        null
                    )
                )
            }
        }

        override fun onBind(item: ViewModel) {
            item.value.setText(inputView)
        }
    }

    // endregion

    // region ViewHolderInflater

    class ViewHolderInflater
        : Item.ViewHolderInflater<ViewHolder> {
        override fun inflate(
            inflater: LayoutInflater,
            parent: ViewGroup,
            listener: (Item.Event) -> Unit
        ): ViewHolder {
            val view = inflater.inflate(R.layout.item_instant_input, parent, false)
            return ViewHolder(view, listener)
        }
    }

    // endregion

}
