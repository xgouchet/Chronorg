package fr.xgouchet.chronorg.data.flow.sink

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.model.RoomEntity

class EntitySink(context: Context) : DataSink<Entity> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun create(entity: Entity): Boolean {
        val result = appDatabase.entityDao().insert(
            RoomEntity(
                project_id = entity.projectId,
                name = entity.name,
                notes = entity.notes,
                birth = entity.birth.toString(),
                death = entity.death.toString()
            )
        )
        return (result > 0)
    }

    override suspend fun update(entity: Entity): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(id: Long): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
