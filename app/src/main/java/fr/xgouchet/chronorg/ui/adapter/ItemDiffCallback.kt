package fr.xgouchet.chronorg.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import fr.xgouchet.chronorg.ui.items.Item

object ItemDiffCallback : DiffUtil.ItemCallback<Item.ViewModel>() {

    override fun areItemsTheSame(oldItem: Item.ViewModel,
                                 newItem: Item.ViewModel)
            : Boolean {
        return oldItem.stableId == newItem.stableId
    }

    override fun areContentsTheSame(oldItem: Item.ViewModel,
                                    newItem: Item.ViewModel)
            : Boolean {
        return oldItem == newItem
    }
}

