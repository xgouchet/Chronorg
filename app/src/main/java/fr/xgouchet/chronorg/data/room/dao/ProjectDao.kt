package fr.xgouchet.chronorg.data.room.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.xgouchet.chronorg.data.room.model.RoomProject

@Dao
interface ProjectDao {

    @Query("SELECT * FROM project")
    suspend fun getAll(): List<RoomProject>

    @Query("SELECT * FROM project WHERE id = :id")
    suspend fun get(id: Long): RoomProject?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(item: RoomProject): Long

    @Update
    suspend fun update(item: RoomProject): Int

    @Delete
    suspend fun delete(item: RoomProject): Int

}
