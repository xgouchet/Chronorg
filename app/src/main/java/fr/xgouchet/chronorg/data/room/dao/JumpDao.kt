package fr.xgouchet.chronorg.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.xgouchet.chronorg.data.room.model.RoomJump

@Dao
interface JumpDao {

    @Query("SELECT * FROM jump")
    suspend fun getAll(): List<RoomJump>

    @Query("SELECT * FROM jump WHERE entity_id = :entityId")
    suspend fun getAllInEntity(entityId : Long): List<RoomJump>

    @Query("SELECT * FROM jump WHERE entity_id NOT IN (:entityIds)")
    suspend fun getAllNotInEntities(entityIds: List<Long>): List<RoomJump>

    @Query("SELECT * FROM jump WHERE id = :id")
    suspend fun get(id: Long): RoomJump?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RoomJump): Long

    @Update
    suspend fun update(item: RoomJump): Int

    @Delete
    suspend fun delete(item: RoomJump): Int

    @Query("DELETE FROM jump WHERE entity_id = :entityId")
    suspend fun deleteAllInProject(entityId: Long): Int
}