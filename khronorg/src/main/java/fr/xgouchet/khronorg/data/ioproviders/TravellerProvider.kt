package fr.xgouchet.khronorg.data.ioproviders

import android.database.Cursor
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.data.queriers.ContentQuerier
import fr.xgouchet.khronorg.data.queriers.TravellerQuerier
import fr.xgouchet.khronorg.data.readers.Reader
import fr.xgouchet.khronorg.data.readers.TravellerReader
import fr.xgouchet.khronorg.data.writers.TravellerWriter
import fr.xgouchet.khronorg.data.writers.Writer

/**
 * @author Xavier F. Gouchet
 */
class TravellerProvider : IOProvider<Traveller> {

    override fun provideReader(cursor: Cursor): Reader<Traveller> = TravellerReader(cursor)

    override val writer: Writer<Traveller> = TravellerWriter()

    override val querier: ContentQuerier<Traveller> = TravellerQuerier(this)

}