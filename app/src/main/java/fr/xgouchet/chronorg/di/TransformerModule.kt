package fr.xgouchet.chronorg.di

import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Event
import fr.xgouchet.chronorg.data.flow.model.Jump
import fr.xgouchet.chronorg.data.flow.model.Portal
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.ui.transformer.EntityListTransformer
import fr.xgouchet.chronorg.ui.transformer.EventListTransformer
import fr.xgouchet.chronorg.ui.transformer.JumpListTransformer
import fr.xgouchet.chronorg.ui.transformer.PortalListTransformer
import fr.xgouchet.chronorg.ui.transformer.ProjectContentTransformer
import fr.xgouchet.chronorg.ui.transformer.ProjectListTransformer
import fr.xgouchet.chronorg.ui.transformer.ViewModelListTransformer
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.provider

val TransformerModule = Kodein.Module(name = "Transformer") {

    bind<ViewModelListTransformer<List<Project>>>() with provider {
        ProjectListTransformer()
    }

    bind<ViewModelListTransformer<Project>>() with provider {
        ProjectContentTransformer()
    }

    bind<ViewModelListTransformer<List<Entity>>>() with provider {
        EntityListTransformer(instance())
    }

    bind<ViewModelListTransformer<List<Portal>>>() with provider {
        PortalListTransformer(instance())
    }

    bind<ViewModelListTransformer<List<Event>>>() with provider {
        EventListTransformer(instance())
    }

    bind<ViewModelListTransformer<List<Jump>>>() with provider {
        JumpListTransformer(instance())
    }
}
