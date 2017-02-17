package fr.xgouchet.khronorg.data.ioproviders

import android.database.Cursor
import fr.xgouchet.khronorg.data.queriers.ContentQuerier
import fr.xgouchet.khronorg.data.readers.Reader
import fr.xgouchet.khronorg.data.writers.Writer

/**
 * @author Xavier F. Gouchet
 */
interface IOProvider<T> {

    fun provideReader(cursor: Cursor): Reader<T>

    val writer: Writer<T>

    val querier: ContentQuerier<T>
}
