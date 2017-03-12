package fr.xgouchet.khronorg.ui.activities

import android.content.Intent
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.navigators.ProjectNavigator
import fr.xgouchet.khronorg.ui.navigators.ProjectNavigator.Companion.EXTRA_PROJECT
import fr.xgouchet.khronorg.ui.navigators.TravellerNavigator
import fr.xgouchet.khronorg.ui.presenters.BaseEditorPresenter
import fr.xgouchet.khronorg.ui.presenters.ProjectEditorPresenter

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