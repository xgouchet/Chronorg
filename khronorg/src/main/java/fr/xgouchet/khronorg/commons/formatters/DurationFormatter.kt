package fr.xgouchet.khronorg.commons.formatters

import org.joda.time.ReadableDuration
import org.joda.time.format.PeriodFormatterBuilder

/**
 * @author Xavier F. Gouchet
 */
object DefaultDurationFormatter : Formatter<ReadableDuration> {


    internal val pf = PeriodFormatterBuilder()
            .appendYears().appendSuffix("y").appendSeparatorIfFieldsBefore(", ")
            .appendMonths().appendSuffix("m").appendSeparatorIfFieldsBefore(", ")
            .appendDays().appendSuffix("d").appendSeparatorIfFieldsBefore(" / ")
            .appendHours().appendSuffix(":")
            .appendMinutes().appendSuffix("'")
            .appendSeconds().appendSuffix("\"")
            .toFormatter()



    override fun format(input: ReadableDuration): String {
        return pf.print(input.toPeriod())
    }
}