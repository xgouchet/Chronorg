package fr.xgouchet.khronorg.data.formatters

import org.joda.time.ReadableInstant
import org.joda.time.format.DateTimeFormat

/**
 * @author Xavier F. Gouchet
 */
object InstantFormatter : Formatter<ReadableInstant> {

    internal val dtf = DateTimeFormat.forStyle("MS").withZoneUTC()

    override fun format(input: ReadableInstant): String {
        return dtf.print(input)
    }
}