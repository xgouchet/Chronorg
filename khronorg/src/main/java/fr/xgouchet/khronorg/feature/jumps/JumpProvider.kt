package fr.xgouchet.khronorg.feature.jumps

import android.database.Cursor
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import fr.xgouchet.khronorg.feature.jumps.Jump
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.commons.queriers.ContentQuerier
import fr.xgouchet.khronorg.feature.jumps.JumpQuerier
import fr.xgouchet.khronorg.feature.travellers.TravellerQuerier
import fr.xgouchet.khronorg.feature.jumps.JumpReader
import fr.xgouchet.khronorg.commons.readers.Reader
import fr.xgouchet.khronorg.feature.travellers.TravellerReader
import fr.xgouchet.khronorg.feature.jumps.JumpWriter
import fr.xgouchet.khronorg.feature.travellers.TravellerWriter
import fr.xgouchet.khronorg.commons.writers.Writer

/**
 * @author Xavier F. Gouchet
 */
class JumpProvider : IOProvider<Jump> {

    override fun provideReader(cursor: Cursor): Reader<Jump> = JumpReader(cursor)

    override val writer: Writer<Jump> = JumpWriter()

    override val querier: ContentQuerier<Jump> = JumpQuerier(this)

}