package fr.xgouchet.khronorg.feature.timeline

import android.graphics.Color
import org.joda.time.ReadableInstant
import java.util.*

/**
 * @author Xavier F. Gouchet
 */
data class TimelineShard(var instant: ReadableInstant,
                         var label: String = "",
                         var color: Int = Color.rgb(0xF6, 0x40, 0x2C),
                         var type: ShardType = ShardType.SINGLE,
                         var id: Long = 0,
                         val prefix: MutableList<TimelineShard?> = ArrayList()) {
    enum class ShardType {
        SINGLE, FIRST, LAST, YEAR;

        override fun toString(): String {
            when (this) {
                SINGLE -> return "()"
                FIRST -> return "->"
                LAST -> return ">|"
                YEAR -> return "××"
            }
        }
    }

    override fun toString(): String {
        return "Shard@$id “$label” [$instant] $type"
    }
}
