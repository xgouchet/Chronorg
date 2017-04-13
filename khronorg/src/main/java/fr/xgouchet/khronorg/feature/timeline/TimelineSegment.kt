package fr.xgouchet.khronorg.feature.timeline

import android.util.Log
import org.joda.time.ReadableInstant
import kotlin.properties.Delegates.notNull

/**
 * @author Xavier F. Gouchet
 */
data class TimelineSegment(var legendFrom: String,
                           var legendDest: String,
                           var from: ReadableInstant,
                           var dest: ReadableInstant,
                           var color: Int) {

    var id: Long by notNull()

    init {
        synchronized(lock) {
            id = instanceCount++
            Log.w("Segment", "Got id $id")
        }
    }

    override fun toString(): String {
        return "Segment {@$id; $legendFrom → $legendDest}"
    }

    companion object {
        protected var instanceCount: Long = 0
        protected val lock = Object()
    }

}