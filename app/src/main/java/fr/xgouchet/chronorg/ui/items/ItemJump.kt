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

class ItemJump {

    // region ViewModel

    data class ViewModel(
        private val index: Item.Index = Item.Index(0, 0),
        val title: TextSource,
        val icon: ImageSource? = null,
        val from: TextSource? = null,
        val to: TextSource? = null,
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

        private val iconView: ImageView = itemView.findViewById(R.id.icon)
        private val titleView: TextView = itemView.findViewById(R.id.title)
        private val jumpFromView: TextView = itemView.findViewById(R.id.jump_from)
        private val jumpToView: TextView = itemView.findViewById(R.id.jump_to)

        override fun onBind(item: ViewModel) {
            iconView.applyOrHide(item.icon) { setImage(it) }
            item.title.setText(titleView)
            jumpFromView.applyOrHide(item.from){setText(it)}
            jumpToView.applyOrHide(item.to){setText(it)}
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