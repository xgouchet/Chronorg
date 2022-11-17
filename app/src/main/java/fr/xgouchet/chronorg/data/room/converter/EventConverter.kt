package fr.xgouchet.chronorg.data.room.converter

import fr.xgouchet.chronorg.data.flow.model.Event
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomEvent
import fr.xgouchet.chronorg.data.room.model.RoomProject
import org.joda.time.DateTime
import org.joda.time.Instant

class EventConverter : RoomConverter<RoomEvent, Event> {

    override fun fromRoom(roomModel: RoomEvent): Event {
        return Event(
            id = roomModel.id,
            project = Project.EMPTY,
            name = roomModel.name,
            notes = roomModel.notes,
            date = Instant(roomModel.date)
        )
    }

    override fun toRoom(appModel: Event): RoomEvent {
        return RoomEvent(
            id = appModel.id,
            project_id = appModel.project.id,
            name = appModel.name,
            notes = appModel.notes,
            date = appModel.date.toString()
        )
    }
}
