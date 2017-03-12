package fr.xgouchet.khronorg.ui.activities

import android.content.Intent
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.navigators.JumpNavigator
import fr.xgouchet.khronorg.ui.navigators.JumpNavigator.Companion.EXTRA_JUMP
import fr.xgouchet.khronorg.ui.navigators.TravellerNavigator
import fr.xgouchet.khronorg.ui.navigators.TravellerNavigator.Companion.EXTRA_TRAVELLER
import fr.xgouchet.khronorg.ui.presenters.BaseEditorPresenter
import fr.xgouchet.khronorg.ui.presenters.JumpEditorPresenter
import fr.xgouchet.khronorg.ui.presenters.TravellerEditorPresenter

/**
 * @author Xavier F. Gouchet
 */
class JumpEditorAktivity : BaseEditorAktivity<Jump>() {


    override fun readItem(intent: Intent?): Jump? {
        intent?.let {
            if (it.hasExtra(EXTRA_JUMP)) {
                return it.getParcelableExtra(EXTRA_JUMP)
            }
        }
        return null
    }

    override fun instantiatePresenter(item: Jump?): BaseEditorPresenter<Jump> {
        val jump = item ?: jumpInCurrentTraveller()
        val repository = kodein.instance<BaseRepository<Jump>>()
        val navigator = JumpNavigator(this)
        return JumpEditorPresenter(jump, repository, navigator, item != null)
    }

    private fun jumpInCurrentTraveller(): Jump {
        val traveller = kodein.instance<BaseRepository<Traveller>>().getCurrent()
        val jump = Jump()
        jump.travellerId = traveller.id
        return jump
    }

}