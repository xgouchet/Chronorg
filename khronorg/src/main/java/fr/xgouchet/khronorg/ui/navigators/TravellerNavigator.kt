package fr.xgouchet.khronorg.ui.navigators

import android.app.Activity
import android.content.Intent
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.ui.activities.ProjectEditorActivity
import fr.xgouchet.khronorg.ui.activities.TravellerEditorActivity

/**
 * @author Xavier F. Gouchet
 */
class TravellerNavigator(val activity: Activity) : Navigator<Traveller> {

    override fun goToItemDetails(item: Traveller) {
//        val intent = Intent(activity, ProjectEditorActivity::class.java)
//        activity.startActivity(intent)
    }

    override fun goToItemEdition(item: Traveller) {
        val intent = Intent(activity, TravellerEditorActivity::class.java)
        // TODO add extra
        activity.startActivity(intent)
    }

    override fun goToItemCreation() {
        val intent = Intent(activity, TravellerEditorActivity::class.java)
        activity.startActivity(intent)
    }

    override fun goBack() {
        activity.finish()
    }
}