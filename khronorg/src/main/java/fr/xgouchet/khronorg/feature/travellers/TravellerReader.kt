package fr.xgouchet.khronorg.feature.travellers

import android.database.Cursor
import fr.xgouchet.khronorg.commons.readers.BaseReader
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.provider.KhronorgSchema
import org.joda.time.DateTime

/**
 * @author Xavier F. Gouchet
 */
class TravellerReader(cursor: Cursor) : BaseReader<Traveller>(cursor) {

    private var idxId: Int = 0
    private var idxProjectId: Int = 0
    private var idxName: Int = 0
    private var idxBirth: Int = 0
    private var idxDeath: Int = 0
    private var idxColor: Int = 0

    init {
        cacheIndices()
    }

    private fun cacheIndices() {
        idxId = getIndex(KhronorgSchema.COL_ID)
        idxProjectId = getIndex(KhronorgSchema.COL_PROJECT_ID)
        idxName = getIndex(KhronorgSchema.COL_NAME)
        idxBirth = getIndex(KhronorgSchema.COL_BIRTH_INSTANT)
        idxDeath = getIndex(KhronorgSchema.COL_DEATH_INSTANT)
        idxColor = getIndex(KhronorgSchema.COL_COLOR)
    }

    override fun instantiate(): Traveller = Traveller()

    override fun fill(data: Traveller) {
        data.id = readInt(idxId)
        data.projectId = readInt(idxProjectId)
        data.name = readString(idxName)
        data.birth = DateTime(readString(idxBirth))
        data.death = DateTime(readString(idxDeath))
        data.color = readInt(idxColor)
    }
}