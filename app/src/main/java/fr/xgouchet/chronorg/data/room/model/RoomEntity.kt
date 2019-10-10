package fr.xgouchet.chronorg.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "entity")
data class RoomEntity(
        @PrimaryKey(autoGenerate = true) var id: Long = 0,
        @ColumnInfo(index = true)  var project_id: Long = 0,
        var name: String = "",
        var notes: String = "",
        var birth: String = "",
        var death: String? = null
)
