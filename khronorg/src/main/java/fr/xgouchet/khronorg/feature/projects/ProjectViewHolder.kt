package fr.xgouchet.khronorg.feature.projects

import android.view.View
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.ui.Cutelry.knife
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import io.reactivex.functions.Consumer

/**
 * @author Xavier F. Gouchet
 */
class ProjectViewHolder(listener: Consumer<Project>?, itemView: View)
    : BaseViewHolder<Project>(listener, itemView) {

    internal val name: TextView by knife(R.id.name, itemView)

    init {
        if (listener != null) {
            itemView.setOnClickListener({ view -> listener.accept(item) })
        }
    }

    override fun onBindItem(item: Project) {

        name.text = item.name
    }
}