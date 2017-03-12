package fr.xgouchet.khronorg.ui.presenters

import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.data.query.QueryAlterer
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.provider.KhronorgSchema.Companion.COL_ORDER
import fr.xgouchet.khronorg.provider.KhronorgSchema.Companion.COL_TRAVELLER_ID
import fr.xgouchet.khronorg.ui.navigators.JumpNavigator
import io.reactivex.Observable

/**
 * @author Xavier F. Gouchet
 */
class JumpListPresenter(val repository: BaseRepository<Jump>, val traveller: Traveller, navigator: JumpNavigator)
    : BaseListPresenter<Jump>(navigator) {


    override fun getItemsObservable(): Observable<Jump> {
        val alteration = QueryAlterer.where {
            equals(COL_TRAVELLER_ID, traveller.id.toString())
            orderAsc(COL_ORDER)
        }

        return repository.getWhere(alteration)
    }

    override fun itemSelected(item: Jump?) {
        if (item == null) {
            navigator.goToItemCreation()
        } else {
            navigator.goToItemDetails(item)
        }
    }

}