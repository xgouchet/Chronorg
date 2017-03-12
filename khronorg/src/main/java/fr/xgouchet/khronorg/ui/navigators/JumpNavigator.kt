package fr.xgouchet.khronorg.ui.navigators

import android.app.Activity
import android.content.Intent
import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.ui.activities.JumpEditorAktivity
import fr.xgouchet.khronorg.ui.activities.TravellerEditorAktivity

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