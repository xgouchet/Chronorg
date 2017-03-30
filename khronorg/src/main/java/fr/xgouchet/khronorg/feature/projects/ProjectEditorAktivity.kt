package fr.xgouchet.khronorg.feature.projects

import android.content.Intent
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.editor.BaseEditorAktivity
import fr.xgouchet.khronorg.feature.projects.ProjectNavigator
import fr.xgouchet.khronorg.feature.projects.ProjectNavigator.Companion.EXTRA_PROJECT
import fr.xgouchet.khronorg.feature.travellers.TravellerNavigator
import fr.xgouchet.khronorg.feature.editor.BaseEditorPresenter
import fr.xgouchet.khronorg.feature.projects.ProjectEditorPresenter

/**
 * @author Xavier F. Gouchet
 */
class ProjectEditorAktivity : BaseEditorAktivity<Project>() {


    override fun readItem(intent: Intent?): Project? {
        intent?.let {
            if (it.hasExtra(EXTRA_PROJECT)) {
                return it.getParcelableExtra(EXTRA_PROJECT)
            }
        }
        return null
    }

    override fun instantiatePresenter(item: Project?): BaseEditorPresenter<Project> {
        val project = item ?: Project()
        val repository = kodein.instance<BaseRepository<Project>>()
        val navigator = ProjectNavigator(this)
        return ProjectEditorPresenter(project, repository, navigator, item != null)
    }

}