package fr.xgouchet.khronorg.feature.projects

import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.editor.items.EditorItem
import fr.xgouchet.khronorg.feature.editor.items.EditorTextItem
import fr.xgouchet.khronorg.ui.navigators.Navigator
import fr.xgouchet.khronorg.feature.editor.BaseEditorPresenter
import fr.xgouchet.khronorg.feature.editor.items.EditorInstantItem
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
                emitter.onNext(EditorInstantItem(RANGE_MIN, R.string.hint_project_range_min, item.min))
                emitter.onNext(EditorInstantItem(RANGE_MAX, R.string.hint_project_range_max, item.max))
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
                RANGE_MIN -> {
                    item.min = (editorItem as EditorInstantItem).instant
                }
                RANGE_MAX -> {
                    item.max = (editorItem as EditorInstantItem).instant
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
        val RANGE_MIN = "range_min"
        val RANGE_MAX = "range_max"
    }
}