package fr.xgouchet.khronorg.ui.presenters

import fr.xgouchet.khronorg.ui.views.View
import kotlin.properties.Delegates.notNull

/**
 * @author Xavier F. Gouchet
 */
class BasePresenter<T : Any> : Presenter<T> {

    var view: View<T> by notNull()

    var data: T? = null

    override fun subscribe() {
        data?.let { view.setContent(it) }
    }

    override fun unsubscribe() {
        // nothing to do
    }

    override fun load(force: Boolean) {
        // nothing to load
    }

}