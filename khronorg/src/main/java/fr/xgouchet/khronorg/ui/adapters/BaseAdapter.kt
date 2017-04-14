package fr.xgouchet.khronorg.ui.adapters

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import java.util.*

/**
 * @author Xavier Gouchet
 */
abstract class BaseAdapter<T>
    : RecyclerView.Adapter<BaseViewHolder<T>>() {

    protected abstract val layoutId: Int

    protected val content = ArrayList<T>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<T> {
        val view = LayoutInflater.from(parent.context).inflate(getLayoutId(viewType), parent, false)

        return instantiateViewHolder(viewType, view)
    }

    open fun getLayoutId(viewType: Int): Int {
        return layoutId
    }

    override fun onBindViewHolder(holder: BaseViewHolder<T>, position: Int) {
        val item = content[position]
        holder.bindItem(item)
    }

    override fun getItemCount(): Int {
        return content.size
    }

    fun update(newContent: List<T>) {
        // TODO use DiffUtils
        content.clear()
        content.addAll(newContent)
        notifyDataSetChanged()
    }

    protected abstract fun instantiateViewHolder(viewType: Int, view: View): BaseViewHolder<T>


}

