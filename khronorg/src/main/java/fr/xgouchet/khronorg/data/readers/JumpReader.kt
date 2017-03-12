package fr.xgouchet.khronorg.data.readers

import android.database.Cursor
import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.provider.KhronorgSchema
import org.joda.time.DateTime
import org.joda.time.Interval

/**
 * @author Xavier F. Gouchet
 */
class JumpReader(cursor: Cursor) : BaseReader<Jump>(cursor) {

    private var idxId: Int = 0
    private var idxTravellerId: Int = 0
    private var idxOrder: Int = 0
    private var idxFrom: Int = 0
    private var idxDelay: Int = 0
    private var idxDirection: Int = 0

    init {
        cacheIndices()
    }

    private fun cacheIndices() {
        idxId = getIndex(KhronorgSchema.COL_ID)
        idxTravellerId = getIndex(KhronorgSchema.COL_TRAVELLER_ID)
        idxOrder = getIndex(KhronorgSchema.COL_ORDER)
        idxFrom = getIndex(KhronorgSchema.COL_FROM_INSTANT)
        idxDelay = getIndex(KhronorgSchema.COL_DELAY)
        idxDirection = getIndex(KhronorgSchema.COL_DIRECTION)
    }

    override fun instantiate(): Jump = Jump()

    override fun fill(data: Jump) {
        data.id = readInt(idxId)
        data.travellerId = readInt(idxTravellerId)
        data.order = readInt(idxOrder)
        data.from = DateTime(readString(idxFrom))
        data.delay = Interval(readString(idxDelay))
        data.direction = readInt(idxDirection)
    }
}