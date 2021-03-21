package fr.xgouchet.chronorg.ui.formatter

import org.joda.time.Instant
import org.joda.time.format.DateTimeFormat
import org.joda.time.format.DateTimeFormatter

class InstantFormatter : Formatter<Instant> {

    val dateTimeFormatter: DateTimeFormatter = DateTimeFormat.forStyle("MS").withZoneUTC()

    override fun format(data: Instant): String {
        return data.toString(dateTimeFormatter)
    }
}