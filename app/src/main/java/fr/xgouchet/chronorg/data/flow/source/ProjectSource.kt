package fr.xgouchet.chronorg.data.flow.source

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomProject

class ProjectSource(
    context: Context,
    private val converter: RoomConverter<RoomProject, Project>
) : DataSource<Project> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun get(id: Long): Project? {
        val roomModel = appDatabase.projectDao().get(id)
        return if (roomModel == null) {
            null
        } else {
            val project = converter.fromRoom(roomModel)
            val entityCount = appDatabase.entityDao().getAllInProject(id).count()
            project.copy(entityCount = entityCount)
        }
    }

    override suspend fun getAll(): List<Project> {
        return appDatabase.projectDao().getAll().map { converter.fromRoom(it) }
    }

    override suspend fun getAllInParent(parentId: Long): List<Project> {
        throw IllegalStateException(
            "Can't get all projects in parent: projects don't have parents."
        )
    }
}
