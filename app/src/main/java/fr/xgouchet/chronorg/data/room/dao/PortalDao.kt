package fr.xgouchet.chronorg.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.xgouchet.chronorg.data.room.model.RoomEntity
import fr.xgouchet.chronorg.data.room.model.RoomPortal
import fr.xgouchet.chronorg.data.room.model.RoomProject

@Dao
interface PortalDao {

    @Query("SELECT * FROM portal")
    suspend fun getAll(): List<RoomPortal>

    @Query("SELECT * FROM portal WHERE project_id = :projectId")
    suspend fun getAllInProject(projectId : Long): List<RoomPortal>

    @Query("SELECT * FROM portal WHERE project_id NOT IN (:projectIds)")
    suspend fun getAllNotInProjects(projectIds: List<Long>): List<RoomPortal>

    @Query("SELECT * FROM portal WHERE id = :id")
    suspend fun get(id: Long): RoomPortal?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RoomPortal): Long

    @Update
    suspend fun update(item: RoomPortal): Int

    @Delete
    suspend fun delete(item: RoomPortal): Int

    @Query("DELETE FROM portal WHERE project_id = :projectId")
    suspend fun deleteAllInProject(projectId: Long): Int

}
