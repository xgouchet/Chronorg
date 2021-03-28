package fr.xgouchet.chronorg.data.flow.sink

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomEntity

class EntitySink(
    context: Context,
    private val converter: RoomConverter<RoomEntity, Entity>
) : DataSink<Entity> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun create(data: Entity): Long {
        return appDatabase.entityDao()
            .insert(converter.toRoom(data))
    }

    override suspend fun update(data
    : Entity): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(data: Entity): Boolean {
        return appDatabase.entityDao()
            .delete(converter.toRoom(data)) == 1
    }
}