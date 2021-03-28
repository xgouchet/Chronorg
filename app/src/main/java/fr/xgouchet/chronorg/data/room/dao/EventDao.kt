package fr.xgouchet.chronorg.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.xgouchet.chronorg.data.room.model.RoomEntity
import fr.xgouchet.chronorg.data.room.model.RoomEvent
import fr.xgouchet.chronorg.data.room.model.RoomPortal
import fr.xgouchet.chronorg.data.room.model.RoomProject

@Dao
interface EventDao {

    @Query("SELECT * FROM event")
    suspend fun getAll(): List<RoomEvent>

    @Query("SELECT * FROM event WHERE project_id = :projectId")
    suspend fun getAllInProject(projectId : Long): List<RoomEvent>

    @Query("SELECT * FROM event WHERE project_id NOT IN (:projectIds)")
    suspend fun getAllNotInProjects(projectIds: List<Long>): List<RoomEvent>

    @Query("SELECT * FROM event WHERE id = :id")
    suspend fun get(id: Long): RoomEvent?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RoomEvent): Long

    @Update
    suspend fun update(item: RoomEvent): Int

    @Delete
    suspend fun delete(item: RoomEvent): Int

    @Query("DELETE FROM event WHERE project_id = :projectId")
    suspend fun deleteAllInProject(projectId: Long): Int
}
