package fr.xgouchet.khronorg.commons.formatters

import org.joda.time.ReadableInstant
import org.joda.time.format.DateTimeFormat

/**
 * @author Xavier F. Gouchet
 */
object DefaultInstantFormatter : Formatter<ReadableInstant> {

    internal val dtf = DateTimeFormat.forStyle("MS").withZoneUTC()

    override fun format(input: ReadableInstant): String {
        return dtf.print(input)
    }
}

object TimelineInstantFormatter : Formatter<ReadableInstant> {

    internal val dtf = DateTimeFormat.forStyle("M-").withZoneUTC()

    override fun format(input: ReadableInstant): String {
        return dtf.print(input)
    }
}

object ShortInstantFormatter : Formatter<ReadableInstant> {

    internal val dtf = DateTimeFormat.forStyle("S-").withZoneUTC()

    override fun format(input: ReadableInstant): String {
        return dtf.print(input)
    }
}