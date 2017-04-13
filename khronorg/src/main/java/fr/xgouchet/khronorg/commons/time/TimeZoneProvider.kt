package fr.xgouchet.khronorg.commons.time

import org.joda.time.DateTimeZone
import java.util.*


/**
 * @author Xavier F. Gouchet
 */
fun getLocalTimeZone(): DateTimeZone {
    val tz = Calendar.getInstance(Locale.getDefault()).timeZone
    return DateTimeZone.forOffsetMillis(tz.rawOffset)
}
