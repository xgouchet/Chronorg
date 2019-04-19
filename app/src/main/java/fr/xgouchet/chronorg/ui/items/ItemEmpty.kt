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

class ItemEmpty {

    // region ViewModel

    data class ViewModel(
            private val index: Item.Index = Item.Index(0, 0),
            val icon: ImageSource? = null,
            val title: TextSource? = null,
            val subtitle: TextSource? = null
    ) : Item.ViewModel() {

        override fun type(): Item.Type = Item.Type.EMPTY

        override fun index(): Item.Index = index

        override fun data(): Any? = null

    }

    // endregion

    // region ViewHolder

    class ViewHolder(
            itemView: View,
            private val listener: (Item.Event) -> Unit
    ) : Item.ViewHolder<ViewModel>(itemView) {

        private val iconView: ImageView = itemView.findViewById(R.id.placeholder)
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val subtitleView: TextView = itemView.findViewById(R.id.subtitle)

        override fun onBind(item: ViewModel) {
            iconView.applyOrHide(item.icon) { setImage(it) }
            titleView.applyOrHide(item.title) { setText(it) }
            subtitleView.applyOrHide(item.subtitle) { setText(it) }
        }
    }

    // endregion

    // region ViewHolderInflater

    class ViewHolderInflater
        : Item.ViewHolderInflater<ViewHolder> {
        override fun inflate(inflater: LayoutInflater,
                             parent: ViewGroup,
                             listener: (Item.Event) -> Unit): ViewHolder {
            val view = inflater.inflate(R.layout.item_empty, parent, false)
            return ViewHolder(view, listener)
        }
    }

    // endregion

}
