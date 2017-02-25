package fr.xgouchet.khronorg.ui.presenters

/**
 * @author Xavier F. Gouchet
 */
interface Presenter<T> {

    fun subscribe()

    fun unsubscribe()

    fun load(force: Boolean = false)
}