package fr.xgouchet.khronorg.feature.jumps

import android.content.Intent
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.feature.jumps.Jump
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.editor.BaseEditorAktivity
import fr.xgouchet.khronorg.feature.jumps.JumpNavigator
import fr.xgouchet.khronorg.feature.jumps.JumpNavigator.Companion.EXTRA_JUMP
import fr.xgouchet.khronorg.feature.travellers.TravellerNavigator
import fr.xgouchet.khronorg.feature.travellers.TravellerNavigator.Companion.EXTRA_TRAVELLER
import fr.xgouchet.khronorg.feature.editor.BaseEditorPresenter
import fr.xgouchet.khronorg.feature.jumps.JumpEditorPresenter
import fr.xgouchet.khronorg.feature.travellers.TravellerEditorPresenter

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