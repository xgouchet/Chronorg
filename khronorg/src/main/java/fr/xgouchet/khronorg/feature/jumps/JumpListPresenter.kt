package fr.xgouchet.khronorg.feature.jumps

import fr.xgouchet.khronorg.feature.jumps.Jump
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.commons.query.QueryBuilder
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.provider.KhronorgSchema.Companion.COL_ORDER
import fr.xgouchet.khronorg.provider.KhronorgSchema.Companion.COL_TRAVELLER_ID
import fr.xgouchet.khronorg.feature.jumps.JumpNavigator
import fr.xgouchet.khronorg.ui.presenters.BaseListPresenter
import io.reactivex.Observable

/**
 * @author Xavier F. Gouchet
 */
class JumpListPresenter(val repository: BaseRepository<Jump>, val traveller: Traveller, navigator: JumpNavigator)
    : BaseListPresenter<Jump>(navigator) {


    override fun getItemsObservable(): Observable<Jump> {
        val alteration = QueryBuilder.where {
            equals(COL_TRAVELLER_ID, traveller.id.toString())
            orderAsc(COL_ORDER)
        }

        return repository.getWhere(alteration)
    }

}