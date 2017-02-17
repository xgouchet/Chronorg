package fr.xgouchet.khronorg.ui.presenters

import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.repositories.ProjectRepository
import fr.xgouchet.khronorg.ui.editor.EditorItem
import fr.xgouchet.khronorg.ui.editor.EditorTextItem
import fr.xgouchet.khronorg.ui.navigators.Navigator
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * @author Xavier F. Gouchet
 */
class ProjectEditorPresenter(item: Project, val repository: ProjectRepository, navigator: Navigator<Project>) : BaseEditorPresenter<Project>(item, navigator) {

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

    companion object {
        val NAME = "name"
    }
}