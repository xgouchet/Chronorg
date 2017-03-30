package fr.xgouchet.khronorg.feature.projects

import android.database.Cursor
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.commons.readers.BaseReader
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class ProjectReader(cursor: Cursor) : BaseReader<Project>(cursor) {

    private var idxId: Int = 0
    private var idxName: Int = 0

    init {
        cacheIndices()
    }

    private fun cacheIndices() {
        idxId = getIndex(KhronorgSchema.COL_ID)
        idxName = getIndex(KhronorgSchema.COL_NAME)
    }

    override fun instantiate(): Project = Project()

    override fun fill(data: Project) {
        data.id = readInt(idxId)
        data.name = readString(idxName)
    }
}