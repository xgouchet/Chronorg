package fr.xgouchet.chronorg.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.xgouchet.chronorg.data.room.model.RoomEntity

@Dao
interface EntityDao {

    @Query("SELECT * FROM entity")
    suspend fun getAll(): List<RoomEntity>

    @Query("SELECT * FROM entity WHERE project_id = :projectId")
    suspend fun getAllInProject(projectId: Long): List<RoomEntity>

    @Query("SELECT * FROM entity WHERE project_id NOT IN (:projectIds)")
    suspend fun getAllNotInProjects(projectIds: List<Long>): List<RoomEntity>

    @Query("SELECT * FROM entity WHERE id = :id")
    suspend fun get(id: Long): RoomEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RoomEntity): Long

    @Update
    suspend fun update(item: RoomEntity): Int

    @Delete
    suspend fun delete(item: RoomEntity): Int

    @Query("DELETE FROM entity WHERE project_id = :projectId")
    suspend fun deleteAllInProject(projectId: Long): Int
}
