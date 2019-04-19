package fr.xgouchet.chronorg.data.room.converter

import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.ProjectDbModel

class ProjectConverter
    : RoomConverter<ProjectDbModel, Project> {

    override fun fromRoom(roomModel: ProjectDbModel): Project {
        return Project(
                id = roomModel.id,
                name = roomModel.name,
                description = roomModel.description
        )
    }

    override fun toRoom(appModel: Project): ProjectDbModel {
        return ProjectDbModel(
                id = appModel.id,
                name = appModel.name,
                description = appModel.description
        )
    }
}
