package fr.xgouchet.chronorg.data.flow.source

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomEntity

class EntitySource(
    context: Context,
    private val converter: RoomConverter<RoomEntity, Entity>
) : DataSource<Entity> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun get(id: Long): Entity? {
        val roomModel = appDatabase.entityDao().get(id)

        return if (roomModel == null) null else converter.fromRoom(roomModel)
    }

    override suspend fun getAll(): List<Entity> {
        return appDatabase.entityDao().getAll().map { converter.fromRoom(it) }
    }

    override suspend fun getAllInParent(parentId: Long): List<Entity> {
        return appDatabase.entityDao().getAllInProject(parentId).map { converter.fromRoom(it) }
    }

    override suspend fun getAllOrphans(): List<Entity> {
        val projectIds = appDatabase.projectDao().getAll().map { it.id }
        return appDatabase.entityDao().getAllNotInProjects(projectIds)
            .map { converter.fromRoom(it) }
    }
}
