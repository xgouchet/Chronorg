package fr.xgouchet.chronorg.data.flow.sink

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Portal
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomPortal

class PortalSink(
    context: Context,
    private val converter: RoomConverter<RoomPortal, Portal>
) : DataSink<Portal> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun create(data: Portal): Long {
        return appDatabase.portalDao()
            .insert(converter.toRoom(data))
    }

    override suspend fun update(data: Portal): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(data: Portal): Boolean {
        return appDatabase.portalDao()
            .delete(converter.toRoom(data)) == 1
    }
}
