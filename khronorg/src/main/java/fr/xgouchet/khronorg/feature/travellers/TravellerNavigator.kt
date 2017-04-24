package fr.xgouchet.khronorg.feature.travellers

import android.app.Activity
import android.content.Intent
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.feature.travellers.TravellerDetailsAktivity
import fr.xgouchet.khronorg.feature.travellers.TravellerEditorAktivity
import fr.xgouchet.khronorg.ui.navigators.Navigator

/**
 * @author Xavier F. Gouchet
 */
class TravellerNavigator(val activity: Activity) : Navigator<Traveller> {
    companion object {
        val EXTRA_TRAVELLER = "project"
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