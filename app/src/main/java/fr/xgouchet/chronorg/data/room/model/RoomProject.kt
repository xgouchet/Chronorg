package fr.xgouchet.chronorg.data.room.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "project")
data class RoomProject(
        @PrimaryKey(autoGenerate = true) var id: Long = 0,
        var name: String = "",
        var description: String = ""
)
