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
            description = roomModel.description,
            entityCount = 0,
            portalCount = 0,
            eventCount = 0
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
