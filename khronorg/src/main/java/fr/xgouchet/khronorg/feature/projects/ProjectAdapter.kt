package fr.xgouchet.khronorg.feature.projects

import android.view.View
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import fr.xgouchet.khronorg.feature.projects.ProjectViewHolder
import io.reactivex.functions.Consumer

/**
 * @author Xavier F. Gouchet
 */
class ProjectAdapter(val listener: Consumer<Project>) : BaseAdapter<Project>() {

    override val layoutId: Int = R.layout.item_project

    override fun instantiateViewHolder(viewType: Int, view: View): BaseViewHolder<Project> {
        return ProjectViewHolder(listener, view)
    }
}