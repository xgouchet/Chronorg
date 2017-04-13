package fr.xgouchet.khronorg.feature.portals

import android.database.Cursor
import fr.xgouchet.khronorg.commons.readers.BaseReader
import fr.xgouchet.khronorg.provider.KhronorgSchema
import org.joda.time.Duration
import org.joda.time.Interval

/**
 * @author Xavier F. Gouchet
 */
class PortalReader(cursor: Cursor) : BaseReader<Portal>(cursor) {

    private var idxId: Int = 0
    private var idxProjectId: Int = 0
    private var idxName: Int = 0
    private var idxDelay: Int = 0
    private var idxDirection: Int = 0
    private var idxColor: Int = 0

    init {
        cacheIndices()
    }

    private fun cacheIndices() {
        idxId = getIndex(KhronorgSchema.COL_ID)
        idxProjectId = getIndex(KhronorgSchema.COL_PROJECT_ID)
        idxName = getIndex(KhronorgSchema.COL_NAME)
        idxDelay = getIndex(KhronorgSchema.COL_DELAY)
        idxDirection = getIndex(KhronorgSchema.COL_DIRECTION)
        idxColor = getIndex(KhronorgSchema.COL_COLOR)
    }

    override fun instantiate(): Portal = Portal()

    override fun fill(data: Portal) {
        data.id = readInt(idxId)
        data.projectId = readInt(idxProjectId)
        data.name = readString(idxName)
        data.delay = Interval(readString(idxDelay))
        data.color = readInt(idxColor)
        data.direction = readInt(idxDirection)
    }
}