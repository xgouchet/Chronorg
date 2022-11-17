package fr.xgouchet.chronorg.data.flow.source

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomEntity

class EntitySource(
    context: Context,
    private val converter: RoomConverter<RoomEntity, Entity>,
    private val projectSource: DataSource<Project>
) : DataSource<Entity> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun get(id: Long): Entity? {
        val roomModel = appDatabase.entityDao().get(id)

        return if (roomModel == null) {
            null
        } else {
            convert(roomModel)
        }
    }

    override suspend fun getAll(): List<Entity> {
        return appDatabase.entityDao().getAll().map { convert(it) }
    }

    override suspend fun getAllInParent(parentId: Long): List<Entity> {
        val project = projectSource.get(parentId)!!
        return appDatabase.entityDao().getAllInProject(parentId)
            .map { converter.fromRoom(it).copy(project = project) }
    }

    override suspend fun getAllOrphans(): List<Entity> {
        val projectIds = appDatabase.projectDao().getAll().map { it.id }
        return appDatabase.entityDao().getAllNotInProjects(projectIds)
            .map { converter.fromRoom(it) }
    }

    private suspend fun convert(roomModel: RoomEntity): Entity {
        val entity = converter.fromRoom(roomModel)
        val project = projectSource.get(roomModel.project_id)!!
        return entity.copy(project = project)
    }
}
