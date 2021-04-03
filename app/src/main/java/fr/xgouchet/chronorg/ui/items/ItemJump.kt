package fr.xgouchet.chronorg.ui.items

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.ui.source.TextSource

class ItemJump {

    // region ViewModel

    data class ViewModel(
        private val index: Item.Index = Item.Index(0, 0),
        val title: TextSource,
        val from: TextSource,
        val to: TextSource,
        val data: Any? = null
    ) : Item.ViewModel() {

        override fun type(): Item.Type = Item.Type.JUMP

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
        private val jumpFromView: TextView = itemView.findViewById(R.id.jump_from)
        private val jumpToView: TextView = itemView.findViewById(R.id.jump_to)

        override fun onBind(item: ViewModel) {
            item.title.setText(titleView)
            item.from.setText(jumpFromView)
            item.to.setText(jumpToView)
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
            val view = inflater.inflate(R.layout.item_jump, parent, false)
            return ViewHolder(view, listener)
        }
    }

    // endregion

}
