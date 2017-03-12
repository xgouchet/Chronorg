package fr.xgouchet.khronorg.data.ioproviders

import android.database.Cursor
import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.data.queriers.ContentQuerier
import fr.xgouchet.khronorg.data.queriers.JumpQuerier
import fr.xgouchet.khronorg.data.queriers.TravellerQuerier
import fr.xgouchet.khronorg.data.readers.JumpReader
import fr.xgouchet.khronorg.data.readers.Reader
import fr.xgouchet.khronorg.data.readers.TravellerReader
import fr.xgouchet.khronorg.data.writers.JumpWriter
import fr.xgouchet.khronorg.data.writers.TravellerWriter
import fr.xgouchet.khronorg.data.writers.Writer

/**
 * @author Xavier F. Gouchet
 */
class JumpProvider : IOProvider<Jump> {

    override fun provideReader(cursor: Cursor): Reader<Jump> = JumpReader(cursor)

    override val writer: Writer<Jump> = JumpWriter()

    override val querier: ContentQuerier<Jump> = JumpQuerier(this)

}