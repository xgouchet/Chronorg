package fr.xgouchet.khronorg.feature.travellers

import android.net.Uri
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import fr.xgouchet.khronorg.commons.queriers.BaseContentQuerier
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class TravellerQuerier(ioProvider: IOProvider<Traveller>) : BaseContentQuerier<Traveller>(ioProvider) {
    override val uri: Uri = KhronorgSchema.TRAVELLERS_URI

    override fun getId(item: Traveller): Int = item.id
}