package fr.xgouchet.chronorg.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.ui.source.ImageSource
import fr.xgouchet.chronorg.ui.source.TextSource
import fr.xgouchet.chronorg.ui.source.applyOrHide
import fr.xgouchet.chronorg.ui.source.asImageSource

class ItemAddInput {

    // region ViewModel

    data class ViewModel(
        private val index: Item.Index = Item.Index(0, 0),
        val icon: ImageSource = R.drawable.ic_add_hex.asImageSource(),
        val data: Any? = null
    ) : Item.ViewModel() {

        override fun type(): Item.Type = Item.Type.ADD_INPUT

        override fun index(): Item.Index = index

        override fun data(): Any? = data
    }

    // endregion

    // region ViewHolder

    class ViewHolder(
        itemView: View,
        private val listener: (Item.Event) -> Unit
    ) : Item.ViewHolder<ViewModel>(itemView) {

        private val iconView: ImageView = itemView.findViewById(R.id.icon)

        init {
            itemView.setOnClickListener {
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
            item.icon.setImage(iconView)
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
            val view = inflater.inflate(R.layout.item_add_between, parent, false)
            return ViewHolder(view, listener)
        }
    }

    // endregion

}
