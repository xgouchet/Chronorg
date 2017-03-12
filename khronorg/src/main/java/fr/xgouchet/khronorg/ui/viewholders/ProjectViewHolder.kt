package fr.xgouchet.khronorg.ui.viewholders

import android.view.View
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.ui.Cutelry.knife
import io.reactivex.functions.Consumer

/**
 * @author Xavier F. Gouchet
 */
class ProjectViewHolder(listener: Consumer<Project>?, itemView: View)
    : BaseViewHolder<Project>(listener, itemView) {

    internal val name: TextView by knife(R.id.name, itemView)

    init {
        if (listener != null) {
            itemView.setOnClickListener({ _ -> listener.accept(item) })
        }
    }

    override fun onBindItem(item: Project) {

        name.text = item.name
    }
}