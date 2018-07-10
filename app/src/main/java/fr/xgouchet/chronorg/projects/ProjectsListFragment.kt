package fr.xgouchet.chronorg.projects

import fr.xgouchet.chronorg.models.Project

class ProjectsListFragment : TAProjectFragment() {

    override val adapter: TAProjectAdapter = ProjectsAdapter()

    override fun navigateToEntity(entity: Project) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}
