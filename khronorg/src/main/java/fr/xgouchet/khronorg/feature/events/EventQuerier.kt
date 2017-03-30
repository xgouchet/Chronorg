package fr.xgouchet.khronorg.feature.events

import android.net.Uri
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import fr.xgouchet.khronorg.commons.queriers.BaseContentQuerier
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class EventQuerier(ioProvider: IOProvider<Event>) : BaseContentQuerier<Event>(ioProvider) {
    override val uri: Uri = KhronorgSchema.EVENTS_URI

    override fun getId(item: Event): Int = item.id
}