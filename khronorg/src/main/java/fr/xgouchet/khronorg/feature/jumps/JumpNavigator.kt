package fr.xgouchet.khronorg.feature.jumps

import android.app.Activity
import android.content.Intent
import fr.xgouchet.khronorg.feature.jumps.Jump
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.feature.jumps.JumpEditorAktivity
import fr.xgouchet.khronorg.feature.travellers.TravellerEditorAktivity
import fr.xgouchet.khronorg.ui.navigators.Navigator

/**
 * @author Xavier F. Gouchet
 */
class JumpNavigator(val activity: Activity) : Navigator<Jump> {
    companion object {
        val EXTRA_JUMP = "jump"
    }

    override fun goToItemDetails(item: Jump) {
        goToItemEdition(item)
    }

    override fun goToItemEdition(item: Jump) {
        val intent = Intent(activity, JumpEditorAktivity::class.java)
        intent.putExtra(EXTRA_JUMP, item)
        activity.startActivity(intent)
    }

    override fun goToItemCreation() {
        val intent = Intent(activity, JumpEditorAktivity::class.java)
        activity.startActivity(intent)
    }

    override fun goBack() {
        activity.finish()
    }
}