package fr.xgouchet.chronorg.data.flow.sink

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Jump
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomJump

class JumpSink(
    context: Context,
    private val converter: RoomConverter<RoomJump, Jump>
) : DataSink<Jump> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun create(data: Jump): Long {
        return appDatabase.jumpDao()
            .insert(converter.toRoom(data))
    }

    override suspend fun update(data
    : Jump): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(data: Jump): Boolean {
        return appDatabase.jumpDao()
            .delete(converter.toRoom(data)) == 1
    }
}
