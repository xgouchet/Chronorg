package fr.xgouchet.chronorg.data.room.converter

import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomEntity
import fr.xgouchet.chronorg.data.room.model.RoomProject
import org.joda.time.DateTime
import org.joda.time.Instant

class EntityConverter
    : RoomConverter<RoomEntity, Entity> {

    override fun fromRoom(roomModel: RoomEntity): Entity {
        return Entity(
            id = roomModel.id,
            projectId = roomModel.project_id,
            name = roomModel.name,
            notes = roomModel.notes,
            birth = Instant(roomModel.birth),
            death = Instant(roomModel.birth)
        )
    }

    override fun toRoom(appModel: Entity): RoomEntity {
        return RoomEntity(
            id = appModel.id,
            project_id = appModel.projectId,
            name = appModel.name,
            notes = appModel.notes,
            birth = appModel.birth.toString(),
            death = appModel.death.toString()
        )
    }
}
