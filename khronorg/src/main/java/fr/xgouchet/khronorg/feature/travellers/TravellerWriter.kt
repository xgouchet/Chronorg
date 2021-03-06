package fr.xgouchet.khronorg.feature.travellers

import android.content.ContentValues
import fr.xgouchet.khronorg.commons.writers.BaseWriter
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class TravellerWriter : BaseWriter<Traveller>() {

    override fun fillContentValues(cv: ContentValues, data: Traveller) {
        cv.put(KhronorgSchema.COL_PROJECT_ID, data.projectId)
        cv.put(KhronorgSchema.COL_NAME, data.name)
        cv.put(KhronorgSchema.COL_BIRTH_INSTANT, data.birth.toString())
        cv.put(KhronorgSchema.COL_DEATH_INSTANT, data.death.toString())
        cv.put(KhronorgSchema.COL_COLOR, data.color)
    }

}