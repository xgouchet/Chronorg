package fr.xgouchet.khronorg.feature.jumps

import android.database.Cursor
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import fr.xgouchet.khronorg.commons.queriers.ContentQuerier
import fr.xgouchet.khronorg.commons.readers.Reader
import fr.xgouchet.khronorg.commons.writers.Writer
import fr.xgouchet.khronorg.feature.portals.Portal
import fr.xgouchet.khronorg.feature.portals.PortalReader
import fr.xgouchet.khronorg.feature.portals.PortalWriter

/**
 * @author Xavier F. Gouchet
 */
class PortalProvider : IOProvider<Portal> {

    override fun provideReader(cursor: Cursor): Reader<Portal> = PortalReader(cursor)

    override val writer: Writer<Portal> = PortalWriter()

    override val querier: ContentQuerier<Portal> = PortalQuerier(this)

}