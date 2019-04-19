package fr.xgouchet.chronorg.di

import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.room.RoomConverter
import fr.xgouchet.chronorg.data.room.converter.ProjectConverter
import fr.xgouchet.chronorg.data.room.model.ProjectDbModel
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

val ConverterModule = Kodein.Module(name = "Converter") {

    bind<RoomConverter<ProjectDbModel, Project>>() with provider { ProjectConverter() }

}

