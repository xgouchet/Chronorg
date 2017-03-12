package fr.xgouchet.khronorg.data.writers

import android.content.ContentValues
import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class JumpWriter : BaseWriter<Jump>() {

    override fun fillContentValues(cv: ContentValues, data: Jump) {
        cv.put(KhronorgSchema.COL_TRAVELLER_ID, data.travellerId)
        cv.put(KhronorgSchema.COL_ORDER, data.order)
        cv.put(KhronorgSchema.COL_FROM_INSTANT, data.from.toString())
        cv.put(KhronorgSchema.COL_DELAY, data.delay.toString())
        cv.put(KhronorgSchema.COL_DIRECTION, data.direction)
    }

}