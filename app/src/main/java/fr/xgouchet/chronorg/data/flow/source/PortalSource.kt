package fr.xgouchet.chronorg.data.flow.source

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Portal
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomPortal

class PortalSource(
    context: Context,
    private val converter: RoomConverter<RoomPortal, Portal>,
    private val projectSource: DataSource<Project>
) : DataSource<Portal> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun get(id: Long): Portal? {
        val roomModel = appDatabase.portalDao().get(id)

        return if (roomModel == null) {
            null
        } else {
            convert(roomModel)
        }
    }

    override suspend fun getAll(): List<Portal> {
        return appDatabase.portalDao().getAll().map { convert(it) }
    }

    override suspend fun getAllInParent(parentId: Long): List<Portal> {
        val project = projectSource.get(parentId)!!
        return appDatabase.portalDao().getAllInProject(parentId)
            .map { converter.fromRoom(it).copy(project = project) }
    }

    override suspend fun getAllOrphans(): List<Portal> {
        val projectIds = appDatabase.projectDao().getAll().map { it.id }
        return appDatabase.portalDao().getAllNotInProjects(projectIds)
            .map { converter.fromRoom(it) }
    }

    private suspend fun convert(roomModel: RoomPortal): Portal {
        val portal = converter.fromRoom(roomModel)
        val project = projectSource.get(roomModel.project_id)!!
        return portal.copy(project = project)
    }
}
