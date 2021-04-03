package fr.xgouchet.chronorg.data.room.converter

import fr.xgouchet.chronorg.data.flow.model.Jump
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomJump
import fr.xgouchet.chronorg.data.room.model.RoomProject
import org.joda.time.DateTime
import org.joda.time.Instant

class JumpConverter : RoomConverter<RoomJump, Jump> {

    override fun fromRoom(roomModel: RoomJump): Jump {
        return Jump(
            id = roomModel.id,
            entityId = roomModel.entity_id,
            name = roomModel.name,
            from = Instant(roomModel.from),
            to = Instant(roomModel.to),
            previousJumpId = roomModel.previous_jump_id
        )
    }

    override fun toRoom(appModel: Jump): RoomJump {
        return RoomJump(
            id = appModel.id,
            entity_id = appModel.entityId,
            name = appModel.name,
            from = appModel.from.toString(),
            to = appModel.to.toString(),
            previous_jump_id = appModel.previousJumpId
        )
    }
}
