package fr.xgouchet.khronorg.data.queriers

import android.net.Uri
import fr.xgouchet.khronorg.data.ioproviders.IOProvider
import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class JumpQuerier(ioProvider: IOProvider<Jump>) : BaseContentQuerier<Jump>(ioProvider) {
    override val uri: Uri = KhronorgSchema.JUMPS_URI

    override fun getId(item: Jump): Int = item.id
}