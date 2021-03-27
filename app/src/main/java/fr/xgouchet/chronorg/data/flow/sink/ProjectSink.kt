package fr.xgouchet.chronorg.data.flow.sink

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomProject

class ProjectSink(
    context: Context,
    private val converter: RoomConverter<RoomProject, Project>
) : DataSink<Project> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun create(data: Project): Long {
        return appDatabase.projectDao()
            .insert(converter.toRoom(data))
    }

    override suspend fun update(data: Project): Boolean {
        return appDatabase.projectDao()
            .update(converter.toRoom(data)) == 1
    }

    override suspend fun delete(data: Project): Boolean {
        return appDatabase.projectDao()
            .delete(converter.toRoom(data)) == 1
    }
}
