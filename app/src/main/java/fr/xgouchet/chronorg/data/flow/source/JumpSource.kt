package fr.xgouchet.chronorg.data.flow.source

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Jump
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomJump

class JumpSource(
    context: Context,
    private val converter: RoomConverter<RoomJump, Jump>
) : DataSource<Jump> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun get(id: Long): Jump? {
        val roomModel = appDatabase.jumpDao().get(id)

        return if (roomModel == null) null else converter.fromRoom(roomModel)
    }

    override suspend fun getAll(): List<Jump> {
        return appDatabase.jumpDao().getAll().map { converter.fromRoom(it) }
    }

    override suspend fun getAllInParent(parentId: Long): List<Jump> {
        return appDatabase.jumpDao().getAllInEntity(parentId).map { converter.fromRoom(it) }
    }

    override suspend fun getAllOrphans(): List<Jump> {
        val entityIds = appDatabase.entityDao().getAll().map { it.id }
        return appDatabase.jumpDao().getAllNotInEntities(entityIds)
            .map { converter.fromRoom(it) }
    }
}
