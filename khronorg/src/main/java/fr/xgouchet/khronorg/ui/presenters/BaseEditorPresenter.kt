package fr.xgouchet.khronorg.ui.presenters

import fr.xgouchet.khronorg.ui.views.EditorView

/**
 * @author Xavier F. Gouchet
 */
open class BaseEditorPresenter<T>(val item: T) : EditorPresenter<T> {

    var view: EditorView<T>? = null


    override fun subscribe() {
        load(force = true)
    }

    override fun unsubscribe() {

    }

    override fun load(force: Boolean) {
        view?.let {
            it.setContent(item)
        }
    }
}