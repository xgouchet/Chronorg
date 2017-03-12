package fr.xgouchet.khronorg.ui.presenters

import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.models.Direction
import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.editor.EditorInstantItem
import fr.xgouchet.khronorg.ui.editor.EditorItem
import fr.xgouchet.khronorg.ui.navigators.Navigator
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import org.joda.time.Interval

/**
 * @author Xavier F. Gouchet
 */
class JumpEditorPresenter(item: Jump, val repository: BaseRepository<Jump>, navigator: Navigator<Jump>, deletable: Boolean)
    : BaseEditorPresenter<Jump>(item, navigator, deletable) {

    override fun getEditorItemsObservable(item: Jump): Observable<EditorItem> {
        val source = ObservableOnSubscribe<EditorItem> {
            emitter ->
            try {
                emitter.onNext(EditorInstantItem(FROM, R.string.hint_jump_from, item.from))
                emitter.onNext(EditorInstantItem(TO, R.string.hint_jump_to, item.destination))
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }

        return Observable.create(source)
    }

    override fun getSaveItemObservable(): Observable<Any> {

        for (editorItem in items) {
            when (editorItem.itemName) {
                FROM -> {
                    item.from = (editorItem as EditorInstantItem).instant
                }
                TO -> {
                    val destination = (editorItem as EditorInstantItem).instant
                    if (destination.isBefore(item.from)) {
                        item.delay = Interval(destination, item.from)
                        item.direction = Direction.PAST
                    } else {
                        item.delay = Interval(item.from, destination)
                        item.direction = Direction.PAST
                    }
                }
            }
        }

        return repository.save(item)
    }

    override fun getDeleteItemObservable():Observable<Any> {
        if (item.id >= 0) {
            return repository.delete(item)
        } else {
            return Observable.error(IllegalArgumentException("Cannot "))
        }
    }

    companion object {
        val FROM = "from"
        val TO = "to"
    }
}