package fr.xgouchet.chronorg.di

import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Portal
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.converter.EntityConverter
import fr.xgouchet.chronorg.data.room.converter.PortalConverter
import fr.xgouchet.chronorg.data.room.converter.ProjectConverter
import fr.xgouchet.chronorg.data.room.model.RoomEntity
import fr.xgouchet.chronorg.data.room.model.RoomPortal
import fr.xgouchet.chronorg.data.room.model.RoomProject
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

val ConverterModule = Kodein.Module(name = "Converter") {

    bind<RoomConverter<RoomProject, Project>>() with provider { ProjectConverter() }
    bind<RoomConverter<RoomEntity, Entity>>() with provider { EntityConverter() }
    bind<RoomConverter<RoomPortal, Portal>>() with provider { PortalConverter() }

}

