package fr.xgouchet.khronorg.commons.formatters

import fr.xgouchet.khronorg.commons.time.getLocalTimeZone
import org.joda.time.ReadableInstant
import org.joda.time.format.DateTimeFormat

/**
 * @author Xavier F. Gouchet
 */
object DefaultInstantFormatter : Formatter<ReadableInstant> {

    internal val dtf = DateTimeFormat.forStyle("MS").withZone(getLocalTimeZone())

    override fun format(input: ReadableInstant): String {
        return dtf.print(input)
    }
}

object TimelineInstantFormatter : Formatter<ReadableInstant> {

    internal val dtf = DateTimeFormat.forStyle("M-").withZone(getLocalTimeZone())

    override fun format(input: ReadableInstant): String {

        return dtf.print(input)
    }
}

object ShortInstantFormatter : Formatter<ReadableInstant> {

    internal val dtf = DateTimeFormat.forStyle("S-").withZone(getLocalTimeZone())

    override fun format(input: ReadableInstant): String {
        return dtf.print(input)
    }
}