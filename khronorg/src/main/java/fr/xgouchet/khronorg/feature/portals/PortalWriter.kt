package fr.xgouchet.khronorg.feature.portals

import android.content.ContentValues
import fr.xgouchet.khronorg.commons.writers.BaseWriter
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class PortalWriter : BaseWriter<Portal>() {

    override fun fillContentValues(cv: ContentValues, data: Portal) {
        cv.put(KhronorgSchema.COL_PROJECT_ID, data.projectId)
        cv.put(KhronorgSchema.COL_NAME, data.name)
        cv.put(KhronorgSchema.COL_DELAY, data.delay.toString())
        cv.put(KhronorgSchema.COL_DIRECTION, data.direction)
        cv.put(KhronorgSchema.COL_COLOR, data.color)
    }

}