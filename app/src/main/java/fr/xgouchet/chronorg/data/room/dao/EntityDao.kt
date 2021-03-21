package fr.xgouchet.chronorg.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.xgouchet.chronorg.data.room.model.RoomEntity
import fr.xgouchet.chronorg.data.room.model.RoomProject

@Dao
interface EntityDao {

    @Query("SELECT * FROM entity")
    suspend fun getAll(): List<RoomEntity>

    @Query("SELECT * FROM entity WHERE project_id = :projectId")
    suspend fun getAllInProject(projectId : Long): List<RoomEntity>

    @Query("SELECT * FROM entity WHERE id = :id")
    suspend fun get(id: Long): RoomEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(project: RoomEntity): Long

    @Update
    suspend fun update(project: RoomEntity): Int

    @Delete
    suspend fun delete(project: RoomEntity): Int

}
