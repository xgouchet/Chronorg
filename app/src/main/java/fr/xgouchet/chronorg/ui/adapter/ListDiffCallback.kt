package fr.xgouchet.chronorg.ui.adapter

import androidx.recyclerview.widget.DiffUtil
import fr.xgouchet.chronorg.ui.items.Item

class ListDiffCallback(
        private val oldData: List<Item.ViewModel>,
        private val newData: List<Item.ViewModel>,
        private val delegate: DiffUtil.ItemCallback<Item.ViewModel>
) : DiffUtil.Callback() {

    // region  DiffUtil.Callback

    override fun getOldListSize(): Int = oldData.size

    override fun getNewListSize(): Int = newData.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return delegate.areItemsTheSame(oldData[oldItemPosition], newData[newItemPosition])
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return delegate.areContentsTheSame(oldData[oldItemPosition], newData[newItemPosition])
    }

    // endregion

}
