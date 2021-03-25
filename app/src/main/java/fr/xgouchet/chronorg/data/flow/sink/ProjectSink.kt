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

    override suspend fun create(entity: Project): Long {
        return appDatabase.projectDao()
            .insert(converter.toRoom(entity))
    }

    override suspend fun update(entity: Project): Boolean {
        return appDatabase.projectDao()
            .update(converter.toRoom(entity)) == 1
    }

    override suspend fun delete(entity: Project): Boolean {
        return appDatabase.projectDao()
            .delete(converter.toRoom(entity)) == 1
    }
}
