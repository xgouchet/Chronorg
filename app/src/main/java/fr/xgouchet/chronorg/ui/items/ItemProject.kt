package fr.xgouchet.chronorg.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.ui.source.TextSource
import fr.xgouchet.chronorg.ui.source.applyOrHide

class ItemProject {

    // region ViewModel

    data class ViewModel(
            val index: Item.Index,
            val title: TextSource,
            val description: TextSource? = null,
            val data: Any? = null
    ) : Item.ViewModel() {

        override fun type(): Item.Type = Item.Type.PROJECT

        override fun index(): Item.Index = index

        override fun data(): Any? = data

    }

    // endregion

    // region ViewHolder

    class ViewHolder(
            itemView: View,
            private val listener: (Item.Event) -> Unit
    ) : Item.ViewHolder<ViewModel>(itemView) {

        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val descriptionView: TextView = itemView.findViewById(R.id.description)

        init {
            itemView.setOnClickListener {
                val event = Item.Event(boundItem, Item.Action.ITEM_TAPPED, null)
                listener(event)
            }
        }

        override fun onBind(item: ViewModel) {
            item.title.setText(titleView)
            descriptionView.applyOrHide(item.description) { setText(it) }
        }
    }

    // endregion

    // region ViewHolderInflater

    class ViewHolderInflater
        : Item.ViewHolderInflater<ViewHolder> {
        override fun inflate(inflater: LayoutInflater,
                             parent: ViewGroup,
                             listener: (Item.Event) -> Unit): ViewHolder {
            val view = inflater.inflate(R.layout.item_project, parent, false)
            return ViewHolder(view, listener)
        }
    }

    // endregion

}
