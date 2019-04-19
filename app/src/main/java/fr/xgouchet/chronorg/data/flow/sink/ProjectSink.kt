package fr.xgouchet.chronorg.data.flow.sink

import android.content.Context
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.AppDatabase
import fr.xgouchet.chronorg.data.room.model.ProjectDbModel

class ProjectSink(context: Context)
    : DataSink<Project> {

    private val appDatabase: AppDatabase = AppDatabase.getInstance(context)

    override suspend fun create(entity: Project): Boolean {
        val result = appDatabase.projectDao().insert(
                ProjectDbModel(name = entity.name, description = entity.description)
        )
        return (result > 0)
    }

    override suspend fun update(entity: Project): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun delete(id: Long): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
