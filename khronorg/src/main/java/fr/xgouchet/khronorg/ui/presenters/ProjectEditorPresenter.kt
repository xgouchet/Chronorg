package fr.xgouchet.khronorg.ui.presenters

import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.editor.EditorItem
import fr.xgouchet.khronorg.ui.editor.EditorTextItem
import fr.xgouchet.khronorg.ui.navigators.Navigator
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * @author Xavier F. Gouchet
 */
class ProjectEditorPresenter(item: Project, val repository: BaseRepository<Project>, navigator: Navigator<Project>, deletable : Boolean)
    : BaseEditorPresenter<Project>(item, navigator, deletable) {

    override fun subscribe() {
        super.subscribe()
        repository.setCurrent(item)
    }

    override fun getEditorItemsObservable(item: Project): Observable<EditorItem> {
        val source = ObservableOnSubscribe<EditorItem> {
            emitter ->
            try {
                emitter.onNext(EditorTextItem(NAME, R.string.hint_name, item.name))
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
    }
}