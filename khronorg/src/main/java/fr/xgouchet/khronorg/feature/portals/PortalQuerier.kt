package fr.xgouchet.khronorg.feature.jumps

import android.net.Uri
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import fr.xgouchet.khronorg.commons.queriers.BaseContentQuerier
import fr.xgouchet.khronorg.feature.portals.Portal
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class PortalQuerier(ioProvider: IOProvider<Portal>) : BaseContentQuerier<Portal>(ioProvider) {
    override val uri: Uri = KhronorgSchema.PORTALS_URI

    override fun getId(item: Portal): Int = item.id
}