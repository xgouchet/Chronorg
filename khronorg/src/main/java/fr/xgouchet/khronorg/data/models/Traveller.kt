package fr.xgouchet.khronorg.data.models

import android.graphics.Color
import android.support.annotation.ColorInt
import org.joda.time.DateTime
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
data class Traveller(var id: Int = -1,
                     var projectId: Int = -1,
                     var name: String = "",
                     var birth: ReadableInstant = DateTime("1970-01-01T00:00:00Z"),
                     var death: ReadableInstant = DateTime("2038-01-19T03:14:17Z"),
                     @ColorInt var color: Int = Color.rgb(0xF6, 0x40, 0x2C))