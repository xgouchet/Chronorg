package fr.xgouchet.khronorg.ui.presenters

import fr.xgouchet.khronorg.ui.editor.EditorItem
import fr.xgouchet.khronorg.ui.navigators.Navigator
import fr.xgouchet.khronorg.ui.views.EditorView
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
 * @author Xavier F. Gouchet
 */
abstract class BaseEditorPresenter<T>(val item: T, val navigator: Navigator<T>) : EditorPresenter<T> {

    var view: EditorView? = null

    var readDisposable: Disposable? = null
    var saveDisposable: Disposable? = null

    val items: MutableList<EditorItem> = ArrayList()

    override fun subscribe() {
        load(force = true)
    }

    override fun unsubscribe() {
        saveDisposable?.dispose()
        readDisposable?.dispose()
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

            readDisposable?.dispose()
            readDisposable = getEditorItemsObservable(item)
                    .toList()
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({ onItemsLoaded(it) }, { onError(it) })
        }
    }

    override fun applyEdition() {

        saveDisposable?.dispose()
        saveDisposable = getSaveItemObservable()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({}, { onError(it) }, { onItemSaved() })
    }

    private fun onError(e: Throwable?) {
        view?.let {
            it.setLoading(false)
            if (e != null) it.setError(e)
        }
    }

    private fun onItemsLoaded(list: List<EditorItem>) {
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

    private fun onItemSaved() {
        navigator.goBack()
    }

    abstract fun getEditorItemsObservable(item: T): Observable<EditorItem>

    abstract fun getSaveItemObservable(): Observable<Any>

}