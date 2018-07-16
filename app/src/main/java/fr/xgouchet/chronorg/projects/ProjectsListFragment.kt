package fr.xgouchet.chronorg.projects

import android.content.Intent
import fr.xgouchet.chronorg.models.Project
import fr.xgouchet.chronorg.project.ProjectActivity

class ProjectsListFragment : TAProjectsFragment() {

    override val adapter: TAProjectsAdapter = ProjectsAdapter { navigateToEntity(it) }

    override fun navigateToEntity(entity: Project) {
        startActivity(Intent(context, ProjectActivity::class.java))
    }

}
