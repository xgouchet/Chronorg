package fr.xgouchet.chronorg.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.xgouchet.chronorg.data.room.model.ProjectDbModel

@Dao
interface ProjectDao {

    @Query("SELECT * FROM project")
    suspend fun getAll(): List<ProjectDbModel>

    @Query("SELECT * FROM project WHERE id = :id")
    suspend fun get(id: Long): ProjectDbModel?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: ProjectDbModel): Long

    @Update
    suspend fun update(project: ProjectDbModel): Int

    @Delete
    suspend fun delete(project: ProjectDbModel): Int

}
