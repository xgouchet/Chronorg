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

class ItemCard {

    // region ViewModel

    data class ViewModel(
        val index: Item.Index,
        val title: TextSource,
        val subtitle: TextSource? = null,
        val description: TextSource? = null,
        val icon: ImageSource? = null,
        val data: Any? = null
    ) : Item.ViewModel() {

        override fun type(): Item.Type = Item.Type.CARD

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
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val subtitleView: TextView = itemView.findViewById(R.id.subtitle)
        private val descriptionView: TextView = itemView.findViewById(R.id.description)

        init {
            itemView.setOnClickListener {
                val event = Item.Event(boundItem, Item.Action.ITEM_TAPPED, null)
                listener(event)
            }
        }

        override fun onBind(item: ViewModel) {
            item.title.setText(titleView)
            subtitleView.applyOrHide(item.subtitle) { setText(it) }
            descriptionView.applyOrHide(item.description) { setText(it) }
            iconView.applyOrHide(item.icon) { setImage(it) }
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
            val view = inflater.inflate(R.layout.item_card, parent, false)
            return ViewHolder(view, listener)
        }
    }

    // endregion

}
