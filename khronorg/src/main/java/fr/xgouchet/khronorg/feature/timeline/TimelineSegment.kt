package fr.xgouchet.khronorg.feature.timeline

import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
data class TimelineSegment(var legendFrom: String,
                           var legendDest: String,
                           var from: ReadableInstant,
                           var dest: ReadableInstant,
                           var color: Int) {

    val id = instanceCount++

    companion object {
        protected var instanceCount: Int = 0
    }

}