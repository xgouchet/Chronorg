package fr.xgouchet.khronorg.ui.presenters

import fr.xgouchet.khronorg.ui.navigators.Navigator
import fr.xgouchet.khronorg.ui.views.ListView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * @author Xavier F. Gouchet
 */
abstract class BaseListPresenter<T>(val navigator: Navigator<T>) : ListPresenter<T> {


    val items: MutableList<T> = ArrayList()

    var disposable: Disposable? = null

    var view: ListView<T>? = null


    override fun subscribe() {
        load(force = true)
    }

    override fun unsubscribe() {
        items.clear()
        disposable?.dispose()
    }

    override fun load(force: Boolean) {
        view?.let {

            if (!force) {
                it.setLoading(false)
                it.setContent(items)
                return@let
            }

            it.setLoading(true)
            items.clear()

            disposable?.dispose()
            disposable = getItemsObservable()
                    .toList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ onItemsLoaded(it) }, { onError(it) })
        }

    }

    override fun itemSelected(item: T?) {
        if (item == null) {
            navigator.goToItemCreation()
        } else {
            navigator.goToItemDetails(item)
        }
    }

    private fun onError(e: Throwable?) {
        view?.let {
            it.setLoading(false)
            if (e != null) it.setError(e)
        }
    }

    private fun onItemsLoaded(list: List<T>) {
        items.addAll(list)

        view?.let {
            it.setLoading(false)
            if (items.isEmpty()) {
                it.setEmpty()
            } else {
                it.setContent(items)
            }
        }
    }


    abstract fun getItemsObservable(): Observable<T>

}