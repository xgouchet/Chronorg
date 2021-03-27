package fr.xgouchet.chronorg.data.flow.sink

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Event
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomEvent

class EventSink(
    context: Context,
    private val converter: RoomConverter<RoomEvent, Event>
) : DataSink<Event> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun create(data: Event): Long {
        return appDatabase.eventDao()
            .insert(converter.toRoom(data))
    }

    override suspend fun update(data
    : Event): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(data: Event): Boolean {
        return appDatabase.eventDao()
            .delete(converter.toRoom(data)) == 1
    }
}
