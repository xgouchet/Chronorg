package fr.xgouchet.khronorg.ui.presenters


/**
 * @author Xavier F. Gouchet
 */
interface ListPresenter<T> : Presenter<List<T>> {

    fun itemCreated()

    fun itemSelected(item: T)

}