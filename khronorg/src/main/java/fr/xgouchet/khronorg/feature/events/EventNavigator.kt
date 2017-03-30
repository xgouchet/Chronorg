package fr.xgouchet.khronorg.feature.events

import android.app.Activity
import android.content.Intent
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.feature.events.EventEditorAktivity
import fr.xgouchet.khronorg.ui.navigators.Navigator

/**
 * @author Xavier F. Gouchet
 */
class EventNavigator(val activity: Activity) : Navigator<Event> {
    companion object {
        val EXTRA_EVENT = "event"
    }

    override fun goToItemDetails(item: Event) {
        val intent = Intent(activity, EventEditorAktivity::class.java)
        intent.putExtra(EXTRA_EVENT, item)
        activity.startActivity(intent)
    }

    override fun goToItemEdition(item: Event) {
        val intent = Intent(activity, EventEditorAktivity::class.java)
        intent.putExtra(EXTRA_EVENT, item)
        activity.startActivity(intent)
    }

    override fun goToItemCreation() {
        val intent = Intent(activity, EventEditorAktivity::class.java)
        activity.startActivity(intent)
    }

    override fun goBack() {
        activity.finish()
    }
}