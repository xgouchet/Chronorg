package fr.xgouchet.chronorg.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import fr.xgouchet.chronorg.ui.items.Item
import timber.log.Timber


class ItemAdapter(
        private val listener: (Item.Event) -> Unit
) : RecyclerView.Adapter<Item.ViewHolder<*>>() {

    private val itemCallback: DiffUtil.ItemCallback<Item.ViewModel> = ItemDiffCallback

    private var data: List<Item.ViewModel> = emptyList()

    init {
        @Suppress("LeakingThis")
        try {
            setHasStableIds(true)
        } catch (e: NullPointerException) {
            Timber.w("#error setting @hasStableIds:true (should only happen in tests)")
        }
    }

    // region RecyclerViw.Adapter

    override fun getItemCount(): Int {
        return data.size
    }

    override fun getItemId(position: Int): Long {
        return getItem(position).stableId
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).type().ordinal
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Item.ViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        val type = Item.Type.values().first { it.ordinal == viewType }

        return type.inflater.inflate(inflater, parent, listener)
    }

    override fun onBindViewHolder(holder: Item.ViewHolder<*>, position: Int) {
        holder.bind(data[position])
    }

    // endregion

    // region ItemAdapter

    fun updateData(data: List<Item.ViewModel>) {
        val callback = ListDiffCallback(this.data, data, itemCallback)
        val updates = DiffUtil.calculateDiff(callback)
        updates.dispatchUpdatesTo(this)
        this.data = data
    }

    // endregion

    // region Internal

    private fun getItem(position: Int): Item.ViewModel {
        return data[position]
    }

    // endregion
}
