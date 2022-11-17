package fr.xgouchet.chronorg.data.flow.source

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Event
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomEvent

class EventSource(
    context: Context,
    private val converter: RoomConverter<RoomEvent, Event>,
    private val projectSource: DataSource<Project>
) : DataSource<Event> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun get(id: Long): Event? {
        val roomModel = appDatabase.eventDao().get(id)

        return if (roomModel == null) {
            null
        } else {
            convert(roomModel)
        }
    }

    override suspend fun getAll(): List<Event> {
        return appDatabase.eventDao().getAll().map { convert(it) }
    }

    override suspend fun getAllInParent(parentId: Long): List<Event> {
        val project = projectSource.get(parentId)!!
        return appDatabase.eventDao().getAllInProject(parentId)
            .map { converter.fromRoom(it).copy(project = project) }
    }

    override suspend fun getAllOrphans(): List<Event> {
        val projectIds = appDatabase.projectDao().getAll().map { it.id }
        return appDatabase.eventDao().getAllNotInProjects(projectIds)
            .map { converter.fromRoom(it) }
    }

    private suspend fun convert(roomModel: RoomEvent): Event {
        val event = converter.fromRoom(roomModel)
        val project = projectSource.get(roomModel.project_id)!!
        return event.copy(project = project)
    }
}
