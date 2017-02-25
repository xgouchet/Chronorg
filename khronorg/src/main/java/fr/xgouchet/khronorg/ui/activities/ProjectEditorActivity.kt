package fr.xgouchet.khronorg.ui.activities

import android.content.Intent
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.data.ioproviders.ProjectProvider
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.navigators.ProjectNavigator
import fr.xgouchet.khronorg.ui.presenters.BaseEditorPresenter
import fr.xgouchet.khronorg.ui.presenters.ProjectEditorPresenter

/**
 * @author Xavier F. Gouchet
 */
class ProjectEditorActivity : BaseEditorActivity<Project>() {


    override fun readItem(intent: Intent?): Project? {
        // TODO extract project from intent
        return null
    }

    override fun instantiatePresenter(item: Project?): BaseEditorPresenter<Project> {
        val project = item ?: Project()
        val repository = kodein.instance<BaseRepository<Project>>()
        val navigator = ProjectNavigator(this)
        return ProjectEditorPresenter(project, repository, navigator)
    }

}