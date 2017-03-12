package fr.xgouchet.khronorg.ui.navigators

import android.app.Activity
import android.content.Intent
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.ui.activities.TravellerDetailsAktivity
import fr.xgouchet.khronorg.ui.activities.TravellerEditorAktivity

/**
 * @author Xavier F. Gouchet
 */
class TravellerNavigator(val activity: Activity) : Navigator<Traveller> {
    companion object {
        val EXTRA_TRAVELLER = "traveller"
    }

    override fun goToItemDetails(item: Traveller) {
        val intent = Intent(activity, TravellerDetailsAktivity::class.java)
        intent.putExtra(EXTRA_TRAVELLER, item)
        activity.startActivity(intent)
    }

    override fun goToItemEdition(item: Traveller) {
        val intent = Intent(activity, TravellerEditorAktivity::class.java)
        intent.putExtra(EXTRA_TRAVELLER, item)
        activity.startActivity(intent)
    }

    override fun goToItemCreation() {
        val intent = Intent(activity, TravellerEditorAktivity::class.java)
        activity.startActivity(intent)
    }

    override fun goBack() {
        activity.finish()
    }
}