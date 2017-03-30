package fr.xgouchet.khronorg.feature.events

import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.commons.query.QueryBuilder
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.provider.KhronorgSchema.Companion.COL_NAME
import fr.xgouchet.khronorg.provider.KhronorgSchema.Companion.COL_PROJECT_ID
import fr.xgouchet.khronorg.feature.events.EventNavigator
import fr.xgouchet.khronorg.ui.presenters.BaseListPresenter
import io.reactivex.Observable

/**
 * @author Xavier F. Gouchet
 */
class EventListPresenter(val repository: BaseRepository<Event>, val project: Project, navigator: EventNavigator)
    : BaseListPresenter<Event>(navigator) {


    override fun getItemsObservable(): Observable<Event> {
        val alteration = QueryBuilder.where {
            equals(COL_PROJECT_ID, project.id.toString())
            orderAsc(COL_NAME)
        }

        return repository.getWhere(alteration)
    }

}