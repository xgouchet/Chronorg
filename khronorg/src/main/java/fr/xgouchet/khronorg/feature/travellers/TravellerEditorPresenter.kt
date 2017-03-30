package fr.xgouchet.khronorg.feature.travellers

import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.feature.travellers.Traveller
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

    override fun getDeleteItemObservable(): Observable<Any> {
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