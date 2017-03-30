package fr.xgouchet.khronorg.feature.jumps

import android.net.Uri
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import fr.xgouchet.khronorg.commons.queriers.BaseContentQuerier
import fr.xgouchet.khronorg.feature.jumps.Jump
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class JumpQuerier(ioProvider: IOProvider<Jump>) : BaseContentQuerier<Jump>(ioProvider) {
    override val uri: Uri = KhronorgSchema.JUMPS_URI

    override fun getId(item: Jump): Int = item.id
}