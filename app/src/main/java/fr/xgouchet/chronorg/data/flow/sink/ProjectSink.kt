package fr.xgouchet.chronorg.data.flow.sink

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.model.RoomProject
import timber.log.Timber

class ProjectSink(context: Context) : DataSink<Project> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun create(entity: Project): Long {
        return appDatabase.projectDao().insert(
            RoomProject(name = entity.name, description = entity.description)
        )
    }

    override suspend fun update(entity: Project): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(entity: Project): Boolean {
        val roomEntity = RoomProject(
            id = entity.id,
            name = entity.name,
            description = entity.description
        )
        val deletedCount = appDatabase.projectDao().delete(roomEntity)
        return deletedCount == 1
    }
}
