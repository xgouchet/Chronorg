package fr.xgouchet.khronorg.feature.events

import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.editor.items.EditorColorItem
import fr.xgouchet.khronorg.feature.editor.items.EditorInstantItem
import fr.xgouchet.khronorg.feature.editor.items.EditorItem
import fr.xgouchet.khronorg.feature.editor.items.EditorTextItem
import fr.xgouchet.khronorg.ui.navigators.Navigator
import fr.xgouchet.khronorg.feature.editor.BaseEditorPresenter
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * @author Xavier F. Gouchet
 */
class EventEditorPresenter(item: Event, val repository: BaseRepository<Event>, navigator: Navigator<Event>, deletable : Boolean)
    : BaseEditorPresenter<Event>(item, navigator, deletable) {

    override fun getEditorItemsObservable(item: Event): Observable<EditorItem> {
        val source = ObservableOnSubscribe<EditorItem> {
            emitter ->
            try {
                emitter.onNext(EditorTextItem(NAME, R.string.hint_name, item.name))
                emitter.onNext(EditorColorItem(COLOR, R.string.hint_color, item.color))
                emitter.onNext(EditorInstantItem(INSTANT, R.string.hint_entity_birth, item.instant))
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
                NAME -> {
                    item.name = (editorItem as EditorTextItem).text
                }
                COLOR -> {
                    item.color = (editorItem as EditorColorItem).color
                }
                INSTANT -> {
                    item.instant = (editorItem as EditorInstantItem).instant
                }
            }
        }

        return repository.save(item)
    }

    override fun getDeleteItemObservable(): Observable<Any> {
        if (item.id >= 0) {
            return repository.delete(item)
        } else {
            return Observable.error(IllegalArgumentException("Cannot "))
        }
    }

    companion object {
        val NAME = "name"
        val INSTANT = "instant"
        val COLOR = "color"
    }
}