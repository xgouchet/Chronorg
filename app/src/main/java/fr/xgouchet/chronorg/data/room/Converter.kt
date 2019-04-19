package fr.xgouchet.chronorg.data.room

interface RoomConverter<RM, AM> {
    fun fromRoom(roomModel: RM): AM

    fun toRoom(appModel: AM): RM
}
