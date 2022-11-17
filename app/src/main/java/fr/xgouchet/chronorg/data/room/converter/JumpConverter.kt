package fr.xgouchet.chronorg.data.room.converter

import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Jump
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.model.RoomJump
import org.joda.time.Instant

class JumpConverter : RoomConverter<RoomJump, Jump> {

    override fun fromRoom(roomModel: RoomJump): Jump {
        return Jump(
            id = roomModel.id,
            entity = Entity.EMPTY,
            name = roomModel.name,
            from = Instant(roomModel.from),
            to = Instant(roomModel.to),
            jumpOrder = roomModel.jump_order,
            portal = null
        )
    }

    override fun toRoom(appModel: Jump): RoomJump {
        return RoomJump(
            id = appModel.id,
            entity_id = appModel.entity.id,
            name = appModel.name,
            from = appModel.from.toString(),
            to = appModel.to.toString(),
            jump_order = appModel.jumpOrder,
            portal_id = appModel.portal?.id
        )
    }
}
