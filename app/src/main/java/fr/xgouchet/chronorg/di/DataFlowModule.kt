package fr.xgouchet.chronorg.di

import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.sink.DataSink
import fr.xgouchet.chronorg.data.flow.sink.EntitySink
import fr.xgouchet.chronorg.data.flow.sink.ProjectSink
import fr.xgouchet.chronorg.data.flow.source.DataSource
import fr.xgouchet.chronorg.data.flow.source.EntitySource
import fr.xgouchet.chronorg.data.flow.source.ProjectSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val DataFlowModule = Kodein.Module(name = "DataFlow") {

    bind<DataSource<Project>>() with provider { ProjectSource(instance(), instance()) }

    bind<DataSink<Project>>() with provider { ProjectSink(instance(), instance()) }

    bind<DataSource<Entity>>() with provider { EntitySource(instance(), instance()) }

    bind<DataSink<Entity>>() with provider { EntitySink(instance(), instance()) }
}

