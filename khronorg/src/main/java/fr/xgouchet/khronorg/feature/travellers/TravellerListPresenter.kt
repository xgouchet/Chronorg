package fr.xgouchet.khronorg.feature.travellers

import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.commons.query.QueryBuilder
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.provider.KhronorgSchema.Companion.COL_NAME
import fr.xgouchet.khronorg.provider.KhronorgSchema.Companion.COL_PROJECT_ID
import fr.xgouchet.khronorg.feature.travellers.TravellerNavigator
import fr.xgouchet.khronorg.ui.presenters.BaseListPresenter
import io.reactivex.Observable

/**
 * @author Xavier F. Gouchet
 */
class TravellerListPresenter(val repository: BaseRepository<Traveller>, val project: Project, navigator: TravellerNavigator)
    : BaseListPresenter<Traveller>(navigator) {


    override fun getItemsObservable(): Observable<Traveller> {
        val alteration = QueryBuilder.where {
            equals(COL_PROJECT_ID, project.id.toString())
            orderAsc(COL_NAME)
        }

        return repository.getWhere(alteration)
    }

}