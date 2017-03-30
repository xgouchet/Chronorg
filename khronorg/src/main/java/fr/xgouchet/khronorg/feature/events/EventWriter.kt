package fr.xgouchet.khronorg.feature.events

import android.content.ContentValues
import fr.xgouchet.khronorg.commons.writers.BaseWriter
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class EventWriter : BaseWriter<Event>() {

    override fun fillContentValues(cv: ContentValues, data: Event) {
        cv.put(KhronorgSchema.COL_PROJECT_ID, data.projectId)
        cv.put(KhronorgSchema.COL_NAME, data.name)
        cv.put(KhronorgSchema.COL_INSTANT, data.instant.toString())
        cv.put(KhronorgSchema.COL_COLOR, data.color)
    }

}