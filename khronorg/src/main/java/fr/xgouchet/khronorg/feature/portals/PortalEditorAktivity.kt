package fr.xgouchet.khronorg.feature.portals

import android.content.Intent
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.editor.BaseEditorAktivity
import fr.xgouchet.khronorg.feature.editor.BaseEditorPresenter
import fr.xgouchet.khronorg.feature.jumps.PortalNavigator
import fr.xgouchet.khronorg.feature.jumps.PortalNavigator.Companion.EXTRA_PORTAL
import fr.xgouchet.khronorg.feature.projects.Project

/**
 * @author Xavier F. Gouchet
 */
class PortalEditorAktivity : BaseEditorAktivity<Portal>() {


    override fun readItem(intent: Intent?): Portal? {
        intent?.let {
            if (it.hasExtra(EXTRA_PORTAL)) {
                return it.getParcelableExtra(EXTRA_PORTAL)
            }
        }
        return null
    }

    override fun instantiatePresenter(item: Portal?): BaseEditorPresenter<Portal> {
        val jump = item ?: portalInCurrentProject()
        val repository = kodein.instance<BaseRepository<Portal>>()
        val navigator = PortalNavigator(this)
        return PortalEditorPresenter(jump, repository, navigator, item != null)
    }

    private fun portalInCurrentProject(): Portal {
        val project = kodein.instance<BaseRepository<Project>>().getCurrent()
        val portal = Portal()
        portal.projectId = project.id
        return portal
    }

}