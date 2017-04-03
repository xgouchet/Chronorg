package fr.xgouchet.khronorg.feature.timeline

import android.app.Activity
import fr.xgouchet.khronorg.feature.timeline.TimelineShard
import fr.xgouchet.khronorg.ui.navigators.Navigator

/**
 * @author Xavier F. Gouchet
 */
class ShardNavigator (val activity : Activity): Navigator<TimelineShard> {
    override fun goToItemDetails(item: TimelineShard) {
    }

    override fun goToItemEdition(item: TimelineShard) {
    }

    override fun goToItemCreation() {
    }

    override fun goBack() {
       activity.finish()
    }
}