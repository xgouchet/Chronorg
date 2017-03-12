package fr.xgouchet.khronorg.ui.presenters

import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.editor.EditorColorItem
import fr.xgouchet.khronorg.ui.editor.EditorInstantItem
import fr.xgouchet.khronorg.ui.editor.EditorItem
import fr.xgouchet.khronorg.ui.editor.EditorTextItem
import fr.xgouchet.khronorg.ui.navigators.Navigator
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * @author Xavier F. Gouchet
 */
class TravellerEditorPresenter(item: Traveller, val repository: BaseRepository<Traveller>, navigator: Navigator<Traveller>, deletable : Boolean)
    : BaseEditorPresenter<Traveller>(item, navigator, deletable) {

    override fun getEditorItemsObservable(item: Traveller): Observable<EditorItem> {
        val source = ObservableOnSubscribe<EditorItem> {
            emitter ->
            try {
                emitter.onNext(EditorTextItem(NAME, R.string.hint_name, item.name))
                emitter.onNext(EditorColorItem(COLOR, R.string.hint_color, item.color))
                emitter.onNext(EditorInstantItem(BIRTH, R.string.hint_entity_birth, item.birth))
                emitter.onNext(EditorInstantItem(DEATH, R.string.hint_entity_death, item.death))
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
                BIRTH -> {
                    item.birth = (editorItem as EditorInstantItem).instant
                }
                DEATH -> {
                    item.death = (editorItem as EditorInstantItem).instant
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
        val NAME = "name"
        val BIRTH = "birth"
        val DEATH = "death"
        val COLOR = "color"
    }
}