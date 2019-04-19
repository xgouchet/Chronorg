package fr.xgouchet.chronorg.data.flow.source

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.ProjectDbModel

class ProjectSource(
        context: Context,
        private val converter: RoomConverter<ProjectDbModel, Project>
) : DataSource<Project> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun get(id: Long): Project? {
        val roomModel = appDatabase.projectDao().get(id)
        return if (roomModel == null) null else converter.fromRoom(roomModel)
    }

    override suspend fun getAll(): List<Project> {
        return appDatabase.projectDao().getAll().map { converter.fromRoom(it) }
    }

}
