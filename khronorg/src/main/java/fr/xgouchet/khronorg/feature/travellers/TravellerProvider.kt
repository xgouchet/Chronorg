package fr.xgouchet.khronorg.feature.travellers

import android.database.Cursor
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.commons.queriers.ContentQuerier
import fr.xgouchet.khronorg.feature.travellers.TravellerQuerier
import fr.xgouchet.khronorg.commons.readers.Reader
import fr.xgouchet.khronorg.feature.travellers.TravellerReader
import fr.xgouchet.khronorg.feature.travellers.TravellerWriter
import fr.xgouchet.khronorg.commons.writers.Writer

/**
 * @author Xavier F. Gouchet
 */
class TravellerProvider : IOProvider<Traveller> {

    override fun provideReader(cursor: Cursor): Reader<Traveller> = TravellerReader(cursor)

    override val writer: Writer<Traveller> = TravellerWriter()

    override val querier: ContentQuerier<Traveller> = TravellerQuerier(this)

}