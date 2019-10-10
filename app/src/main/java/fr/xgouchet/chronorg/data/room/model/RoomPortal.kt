package fr.xgouchet.chronorg.data.room.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "portal")
data class RoomPortal(
        @PrimaryKey(autoGenerate = true) var id: Long = 0,
        @ColumnInfo(index = true) var project_id: Long = 0,
        var name: String = "",
        var delay: String = "",
        var direction: String = ""
)
