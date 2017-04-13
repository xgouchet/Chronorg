package fr.xgouchet.khronorg.feature.jumps

import android.app.Activity
import android.content.Intent
import fr.xgouchet.khronorg.feature.portals.Portal
import fr.xgouchet.khronorg.feature.portals.PortalEditorAktivity
import fr.xgouchet.khronorg.ui.navigators.Navigator

/**
 * @author Xavier F. Gouchet
 */
class PortalNavigator(val activity: Activity) : Navigator<Portal> {
    companion object {
        val EXTRA_PORTAL = "portal"
    }

    override fun goToItemDetails(item: Portal) {
        goToItemEdition(item)
    }

    override fun goToItemEdition(item: Portal) {
        val intent = Intent(activity, PortalEditorAktivity::class.java)
        intent.putExtra(EXTRA_PORTAL, item)
        activity.startActivity(intent)
    }

    override fun goToItemCreation() {
        val intent = Intent(activity, PortalEditorAktivity::class.java)
        activity.startActivity(intent)
    }

    override fun goBack() {
        activity.finish()
    }
}