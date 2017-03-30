package fr.xgouchet.khronorg.feature.travellers

import android.content.Intent
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.editor.BaseEditorAktivity
import fr.xgouchet.khronorg.feature.travellers.TravellerNavigator
import fr.xgouchet.khronorg.feature.travellers.TravellerNavigator.Companion.EXTRA_TRAVELLER
import fr.xgouchet.khronorg.feature.editor.BaseEditorPresenter
import fr.xgouchet.khronorg.feature.travellers.TravellerEditorPresenter

/**
 * @author Xavier F. Gouchet
 */
class TravellerEditorAktivity : BaseEditorAktivity<Traveller>() {


    override fun readItem(intent: Intent?): Traveller? {
        intent?.let {
            if (it.hasExtra(EXTRA_TRAVELLER)) {
                return it.getParcelableExtra(EXTRA_TRAVELLER)
            }
        }
        return null
    }

    override fun instantiatePresenter(item: Traveller?): BaseEditorPresenter<Traveller> {
        val traveller = item ?: travellerInCurrentProject()
        val repository = kodein.instance<BaseRepository<Traveller>>()
        val navigator = TravellerNavigator(this)
        return TravellerEditorPresenter(traveller, repository, navigator, item != null)
    }

    private fun travellerInCurrentProject(): Traveller {
        val project = kodein.instance<BaseRepository<Project>>().getCurrent()
        val traveller = Traveller()
        traveller.projectId = project.id
        return traveller
    }

}