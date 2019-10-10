package fr.xgouchet.chronorg.data.room.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey

class RoomEvent(
        @PrimaryKey(autoGenerate = true) var id: Long = 0,
        @ColumnInfo(index = true) var project_id: Long = 0,
        @ColumnInfo(index = true) var entity_id: Long? = null,
        var name: String = "",
        var start: String = "",
        var end: String? = null
)
