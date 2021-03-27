package fr.xgouchet.chronorg.ui.formatter

import org.joda.time.Instant
import org.joda.time.Interval
import org.joda.time.format.PeriodFormatterBuilder

class IntervalFormatter : Formatter<Interval> {

    private var periodFormatter = PeriodFormatterBuilder()
        .appendYears().appendSuffix("y").appendSeparatorIfFieldsBefore(", ")
        .appendMonths().appendSuffix("m").appendSeparatorIfFieldsBefore(", ")
        .appendDays().appendSuffix("d").appendSeparatorIfFieldsBefore(" / ")
        .appendHours().appendSuffix(":")
        .appendMinutes().appendSuffix("'")
        .appendSeconds().appendSuffix("\"")
        .toFormatter()

    override fun format(data: Interval): String {
        return periodFormatter.print(data.toPeriod())
    }
}