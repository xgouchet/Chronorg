package fr.xgouchet.khronorg.feature.portals

import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.data.models.Direction
import fr.xgouchet.khronorg.feature.editor.BaseEditorPresenter
import fr.xgouchet.khronorg.feature.editor.items.EditorColorItem
import fr.xgouchet.khronorg.feature.editor.items.EditorInstantItem
import fr.xgouchet.khronorg.feature.editor.items.EditorItem
import fr.xgouchet.khronorg.feature.editor.items.EditorTextItem
import fr.xgouchet.khronorg.ui.navigators.Navigator
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import org.joda.time.DateTime
import org.joda.time.Interval
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
class PortalEditorPresenter(item: Portal, val repository: BaseRepository<Portal>, navigator: Navigator<Portal>, deletable: Boolean)
    : BaseEditorPresenter<Portal>(item, navigator, deletable) {

    override fun getEditorItemsObservable(item: Portal): Observable<EditorItem> {
        val source = ObservableOnSubscribe<EditorItem> {
            emitter ->
            try {

                val to: ReadableInstant
                when (item.direction) {
                    Direction.FUTURE -> to = FROM.plus(item.delay.toDuration())
                    Direction.PAST -> to = FROM.minus(item.delay.toDuration())
                    else -> throw IllegalStateException("Unknown direction ${item.direction}")
                }
                emitter.onNext(EditorInstantItem(TO, R.string.hint_portal_delay, to))
                emitter.onNext(EditorTextItem(NAME, R.string.hint_portal_name, item.name))
                emitter.onNext(EditorColorItem(COLOR, R.string.hint_color, item.color))
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
                TO -> {
                    val destination = (editorItem as EditorInstantItem).instant
                    if (destination.isBefore(FROM)) {
                        item.delay = Interval(destination, FROM)
                        item.direction = Direction.PAST
                    } else {
                        item.delay = Interval(FROM, destination)
                        item.direction = Direction.FUTURE
                    }
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
        val FROM = DateTime("2000-01-01T00:00:00")

        val NAME = "name"
        val TO = "to"
        val COLOR = "color"


    }
}