package fr.xgouchet.khronorg.data.queriers

import android.net.Uri
import fr.xgouchet.khronorg.data.ioproviders.IOProvider
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class TravellerQuerier(ioProvider: IOProvider<Traveller>) : BaseContentQuerier<Traveller>(ioProvider) {
    override val uri: Uri = KhronorgSchema.TRAVELLERS_URI

    override fun getId(item: Traveller): Int = item.id
}