package fr.xgouchet.chronorg.data.flow.sink

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.model.RoomEntity
import fr.xgouchet.chronorg.data.room.model.RoomProject

class EntitySink(context: Context) : DataSink<Entity> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun create(entity: Entity): Long {
        return appDatabase.entityDao().insert(
            RoomEntity(
                project_id = entity.projectId,
                name = entity.name,
                notes = entity.notes,
                birth = entity.birth.toString(),
                death = entity.death.toString()
            )
        )
    }

    override suspend fun update(entity: Entity): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(entity: Entity): Boolean {
        val roomEntity =  RoomEntity(
            id = entity.id,
            project_id = entity.projectId,
            name = entity.name,
            notes = entity.notes,
            birth = entity.birth.toString(),
            death = entity.death.toString()
        )
        val deletedCount = appDatabase.entityDao().delete(roomEntity)
        return deletedCount == 1
    }
}
