package fr.xgouchet.khronorg.feature.jumps

import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.editor.BaseEditorPresenter
import fr.xgouchet.khronorg.feature.editor.items.EditorInstantItem
import fr.xgouchet.khronorg.feature.editor.items.EditorItem
import fr.xgouchet.khronorg.feature.editor.items.EditorPortalItem
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.ui.navigators.Navigator
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe

/**
 * @author Xavier F. Gouchet
 */
class JumpThroughEditorPresenter(item: Jump,
                                 val repository: BaseRepository<Jump>,
                                 navigator: Navigator<Jump>,
                                 val project: Project,
                                 deletable: Boolean)
    : BaseEditorPresenter<Jump>(item, navigator, deletable) {

    override fun getEditorItemsObservable(item: Jump): Observable<EditorItem> {
        val source = ObservableOnSubscribe<EditorItem> {
            emitter ->
            try {
                emitter.onNext(EditorInstantItem(FROM, R.string.hint_jump_from, item.from))
                emitter.onNext(EditorPortalItem(THROUGH, R.string.hint_jump_through, project, null))
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
                THROUGH -> {
                    val portal = (editorItem as EditorPortalItem).portal
                    portal?.let {
                        item.delay = portal.delay
                        item.direction = portal.direction
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
        val FROM = "from"
        val THROUGH = "to"
    }
}