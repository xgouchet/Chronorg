package fr.xgouchet.chronorg.data.room.converter

import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomProject

class ProjectConverter
    : RoomConverter<RoomProject, Project> {

    override fun fromRoom(roomModel: RoomProject): Project {
        return Project(
                id = roomModel.id,
                name = roomModel.name,
                description = roomModel.description
        )
    }

    override fun toRoom(appModel: Project): RoomProject {
        return RoomProject(
                id = appModel.id,
                name = appModel.name,
                description = appModel.description
        )
    }
}
