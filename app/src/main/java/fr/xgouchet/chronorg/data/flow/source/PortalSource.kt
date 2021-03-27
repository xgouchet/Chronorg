package fr.xgouchet.chronorg.data.flow.source

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Portal
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomPortal

class PortalSource(
    context: Context,
    private val converter: RoomConverter<RoomPortal, Portal>
) : DataSource<Portal> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun get(id: Long): Portal? {
        val roomModel = appDatabase.portalDao().get(id)

        return if (roomModel == null) null else converter.fromRoom(roomModel)
    }

    override suspend fun getAll(): List<Portal> {
        return appDatabase.portalDao().getAll().map { converter.fromRoom(it) }
    }

    override suspend fun getAllInParent(parentId: Long): List<Portal> {
        return appDatabase.portalDao().getAllInProject(parentId).map { converter.fromRoom(it) }
    }
}
