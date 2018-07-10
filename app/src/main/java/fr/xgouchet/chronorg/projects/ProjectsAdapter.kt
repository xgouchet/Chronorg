package fr.xgouchet.chronorg.projects

import android.view.View
import fr.xgouchet.chronorg.R

class ProjectsAdapter : TAProjectAdapter() {
    override val layoutId: Int = R.layout.item_project

    override fun instantiateViewHolder(view: View, viewType: Int): TAProjectViewHolder {
        return ProjectViewHolder(view)
    }
}