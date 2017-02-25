package fr.xgouchet.khronorg.ui.presenters

import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.data.query.QueryAlterer
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.provider.KhronorgSchema.Companion.COL_NAME
import fr.xgouchet.khronorg.provider.KhronorgSchema.Companion.COL_PROJECT_ID
import fr.xgouchet.khronorg.ui.navigators.TravellerNavigator
import io.reactivex.Observable

/**
 * @author Xavier F. Gouchet
 */
class TravellerListPresenter(val repository: BaseRepository<Traveller>, val project: Project, navigator: TravellerNavigator)
    : BaseListPresenter<Traveller>(navigator) {


    override fun getItemsObservable(): Observable<Traveller> {
        val alteration = QueryAlterer.where {
            equals(COL_PROJECT_ID, project.id.toString())
            orderAsc(COL_NAME)
        }

        return repository.getWhere(alteration)
    }

}