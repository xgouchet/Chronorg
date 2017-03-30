package fr.xgouchet.khronorg.feature.events

import android.database.Cursor
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.commons.queriers.ContentQuerier
import fr.xgouchet.khronorg.feature.events.EventQuerier
import fr.xgouchet.khronorg.commons.readers.Reader
import fr.xgouchet.khronorg.feature.events.EventReader
import fr.xgouchet.khronorg.feature.events.EventWriter
import fr.xgouchet.khronorg.commons.writers.Writer

/**
 * @author Xavier F. Gouchet
 */
class EventProvider : IOProvider<Event> {

    override fun provideReader(cursor: Cursor): Reader<Event> = EventReader(cursor)

    override val writer: Writer<Event> = EventWriter()

    override val querier: ContentQuerier<Event> = EventQuerier(this)

}