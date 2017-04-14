package fr.xgouchet.khronorg.feature.projects

import android.database.Cursor
import fr.xgouchet.khronorg.commons.readers.BaseReader
import fr.xgouchet.khronorg.commons.time.getLocalTimeZone
import fr.xgouchet.khronorg.provider.KhronorgSchema
import org.joda.time.DateTime

/**
 * @author Xavier F. Gouchet
 */
class ProjectReader(cursor: Cursor) : BaseReader<Project>(cursor) {

    private var idxId: Int = 0
    private var idxName: Int = 0
    private var idxRangeMin: Int = 0
    private var idxRangeMax: Int = 0
    init {
        cacheIndices()
    }



    private fun cacheIndices() {
        idxId = getIndex(KhronorgSchema.COL_ID)
        idxName = getIndex(KhronorgSchema.COL_NAME)
        idxRangeMin = getIndex(KhronorgSchema.COL_RANGE_MIN_INSTANT)
        idxRangeMax = getIndex(KhronorgSchema.COL_RANGE_MAX_INSTANT)
    }

    override fun instantiate(): Project = Project()

    override fun fill(data: Project) {
        data.id = readInt(idxId)
        data.name = readString(idxName)
        data.min = DateTime(readString(idxRangeMin), getLocalTimeZone())
        data.max = DateTime(readString(idxRangeMax), getLocalTimeZone())
    }
}