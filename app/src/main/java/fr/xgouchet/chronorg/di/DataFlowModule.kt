package fr.xgouchet.chronorg.di

import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Event
import fr.xgouchet.chronorg.data.flow.model.Jump
import fr.xgouchet.chronorg.data.flow.model.Portal
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.sink.DataSink
import fr.xgouchet.chronorg.data.flow.sink.EntitySink
import fr.xgouchet.chronorg.data.flow.sink.EventSink
import fr.xgouchet.chronorg.data.flow.sink.JumpSink
import fr.xgouchet.chronorg.data.flow.sink.PortalSink
import fr.xgouchet.chronorg.data.flow.sink.ProjectSink
import fr.xgouchet.chronorg.data.flow.source.DataSource
import fr.xgouchet.chronorg.data.flow.source.EntitySource
import fr.xgouchet.chronorg.data.flow.source.EventSource
import fr.xgouchet.chronorg.data.flow.source.JumpSource
import fr.xgouchet.chronorg.data.flow.source.PortalSource
import fr.xgouchet.chronorg.data.flow.source.ProjectSource
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val DataFlowModule = Kodein.Module(name = "DataFlow") {

    bind<DataSource<Project>>() with provider { ProjectSource(instance(), instance()) }
    bind<DataSink<Project>>() with provider { ProjectSink(instance(), instance()) }

    bind<DataSource<Entity>>() with provider { EntitySource(instance(), instance(), instance()) }
    bind<DataSink<Entity>>() with provider { EntitySink(instance(), instance()) }

    bind<DataSource<Portal>>() with provider { PortalSource(instance(), instance(), instance()) }
    bind<DataSink<Portal>>() with provider { PortalSink(instance(), instance()) }

    bind<DataSource<Event>>() with provider { EventSource(instance(), instance(), instance()) }
    bind<DataSink<Event>>() with provider { EventSink(instance(), instance()) }

    bind<DataSource<Jump>>() with provider {
        JumpSource(instance(), instance(), instance(), instance())
    }
    bind<DataSink<Jump>>() with provider { JumpSink(instance(), instance()) }
}

