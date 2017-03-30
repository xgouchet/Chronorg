package fr.xgouchet.khronorg.feature.events

import android.database.Cursor
import fr.xgouchet.khronorg.commons.readers.BaseReader
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.provider.KhronorgSchema
import org.joda.time.DateTime

/**
 * @author Xavier F. Gouchet
 */
class EventReader(cursor: Cursor) : BaseReader<Event>(cursor) {

    private var idxId: Int = 0
    private var idxProjectId: Int = 0
    private var idxName: Int = 0
    private var idxInstant: Int = 0
    private var idxColor: Int = 0

    init {
        cacheIndices()
    }

    private fun cacheIndices() {
        idxId = getIndex(KhronorgSchema.COL_ID)
        idxProjectId = getIndex(KhronorgSchema.COL_PROJECT_ID)
        idxName = getIndex(KhronorgSchema.COL_NAME)
        idxInstant = getIndex(KhronorgSchema.COL_INSTANT)
        idxColor = getIndex(KhronorgSchema.COL_COLOR)
    }

    override fun instantiate(): Event = Event()

    override fun fill(data: Event) {
        data.id = readInt(idxId)
        data.projectId = readInt(idxProjectId)
        data.name = readString(idxName)
        data.instant= DateTime(readString(idxInstant))
        data.color = readInt(idxColor)
    }
}