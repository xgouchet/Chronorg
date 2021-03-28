package fr.xgouchet.chronorg.data.flow.source

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Event
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomEvent

class EventSource(
    context: Context,
    private val converter: RoomConverter<RoomEvent, Event>
) : DataSource<Event> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun get(id: Long): Event? {
        val roomModel = appDatabase.eventDao().get(id)

        return if (roomModel == null) null else converter.fromRoom(roomModel)
    }

    override suspend fun getAll(): List<Event> {
        return appDatabase.eventDao().getAll().map { converter.fromRoom(it) }
    }

    override suspend fun getAllInParent(parentId: Long): List<Event> {
        return appDatabase.eventDao().getAllInProject(parentId).map { converter.fromRoom(it) }
    }

    override suspend fun getAllOrphans(): List<Event> {
        val projectIds = appDatabase.projectDao().getAll().map { it.id }
        return appDatabase.eventDao().getAllNotInProjects(projectIds)
            .map { converter.fromRoom(it) }
    }
}
