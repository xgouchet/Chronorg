package fr.xgouchet.chronorg.data.flow.source

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Jump
import fr.xgouchet.chronorg.data.flow.model.Portal
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomJump

class JumpSource(
    context: Context,
    private val converter: RoomConverter<RoomJump, Jump>,
    private val entitySource: DataSource<Entity>,
    private val portalSource: DataSource<Portal>
) : DataSource<Jump> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun get(id: Long): Jump? {
        val roomModel = appDatabase.jumpDao().get(id)
        return if (roomModel == null) {
            null
        } else {
            convert(roomModel)
        }
    }

    override suspend fun getAll(): List<Jump> {
        return appDatabase.jumpDao().getAll()
            .map { convert(it) }
    }

    override suspend fun getAllInParent(parentId: Long): List<Jump> {
        return appDatabase.jumpDao().getAllInEntity(parentId).map { convert(it) }
    }

    override suspend fun getAllOrphans(): List<Jump> {
        val entityIds = appDatabase.entityDao().getAll().map { it.id }
        return appDatabase.jumpDao().getAllNotInEntities(entityIds)
            .map { converter.fromRoom(it) }
    }

    private suspend fun convert(roomModel: RoomJump): Jump {
        val jump = converter.fromRoom(roomModel)
        val entity = entitySource.get(roomModel.entity_id)!!
        val portalId = roomModel.portal_id
        val portal = portalSource.get(portalId ?: 0)
        return jump.copy(entity = entity, portal = portal)
    }
}
