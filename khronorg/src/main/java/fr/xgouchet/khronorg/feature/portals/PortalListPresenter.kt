package fr.xgouchet.khronorg.feature.jumps

import fr.xgouchet.khronorg.commons.query.QueryBuilder
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.portals.Portal
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.provider.KhronorgSchema.Companion.COL_PROJECT_ID
import fr.xgouchet.khronorg.ui.presenters.BaseListPresenter
import io.reactivex.Observable

/**
 * @author Xavier F. Gouchet
 */
class PortalListPresenter(val repository: BaseRepository<Portal>, val traveller: Project, navigator: PortalNavigator)
    : BaseListPresenter<Portal>(navigator) {


    override fun getItemsObservable(): Observable<Portal> {
        val alteration = QueryBuilder.where {
            equals(COL_PROJECT_ID, traveller.id.toString())
        }

        return repository.getWhere(alteration)
    }

    override fun itemSelected(item: Portal?) {
        if (item == null) {
            navigator.goToItemCreation()
        } else {
            navigator.goToItemDetails(item)
        }
    }

}