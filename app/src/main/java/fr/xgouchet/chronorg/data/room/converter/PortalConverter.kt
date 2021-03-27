package fr.xgouchet.chronorg.data.room.converter

import fr.xgouchet.chronorg.data.flow.model.Portal
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomPortal
import org.joda.time.Interval

class PortalConverter
    : RoomConverter<RoomPortal, Portal> {

    override fun fromRoom(roomModel: RoomPortal): Portal {
        return Portal(
            id = roomModel.id,
            projectId = roomModel.project_id,
            name = roomModel.name,
            notes = roomModel.notes,
            delay = Interval.parse(roomModel.delay),
            direction = roomModel.direction
        )
    }

    override fun toRoom(appModel: Portal): RoomPortal {
        return RoomPortal(
            id = appModel.id,
            project_id = appModel.projectId,
            name = appModel.name,
            notes = appModel.notes,
            delay = appModel.delay.toString(),
            direction = appModel.direction
        )
    }
}
