package fr.xgouchet.khronorg.ui.activities

import android.content.Intent
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.navigators.TravellerNavigator
import fr.xgouchet.khronorg.ui.navigators.TravellerNavigator.Companion.EXTRA_TRAVELLER
import fr.xgouchet.khronorg.ui.presenters.BaseEditorPresenter
import fr.xgouchet.khronorg.ui.presenters.TravellerEditorPresenter

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