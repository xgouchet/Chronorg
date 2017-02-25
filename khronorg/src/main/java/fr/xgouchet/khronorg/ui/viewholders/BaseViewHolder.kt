package fr.xgouchet.khronorg.ui.viewholders

import android.support.v7.widget.RecyclerView
import android.view.View
import io.reactivex.functions.Consumer

/**
 * @author Xavier Gouchet
 */
abstract class BaseViewHolder<T>(val listener: Consumer<T>?, itemView: View) : RecyclerView.ViewHolder(itemView) {

    var item: T? = null


    fun bindItem(item: T) {
        this.item = item
        onBindItem(item)
    }

    protected fun fireSelected() {
        item?.let {
            listener?.accept(it)
        }
    }

    protected abstract fun onBindItem(item: T)
}
