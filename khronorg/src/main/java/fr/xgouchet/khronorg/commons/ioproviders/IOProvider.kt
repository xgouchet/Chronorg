package fr.xgouchet.khronorg.commons.ioproviders

import android.database.Cursor
import fr.xgouchet.khronorg.commons.queriers.ContentQuerier
import fr.xgouchet.khronorg.commons.readers.Reader
import fr.xgouchet.khronorg.commons.writers.Writer

/**
 * @author Xavier F. Gouchet
 */
interface IOProvider<T> {

    fun provideReader(cursor: Cursor): Reader<T>

    val writer: Writer<T>

    val querier: ContentQuerier<T>
}
