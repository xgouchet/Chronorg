package fr.xgouchet.chronorg.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "jump")
data class RoomJump(
        @PrimaryKey(autoGenerate = true) var id: Long = 0,
        @ColumnInfo(index = true)  var entity_id: Long = 0,
        var name: String = "",
        var from: String = "",
        var to: String = "",
        var previous_jump_id: Long? = null
)
