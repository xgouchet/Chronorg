package fr.xgouchet.chronorg.projects

import android.view.View
import android.widget.TextView
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.models.Project

class ProjectViewHolder(itemView: View) : TAProjectViewHolder(itemView) {

    private val name: TextView = itemView.findViewById(R.id.name)

    override fun onBindItem(item: Project) {
        name.text = item.name
    }
}