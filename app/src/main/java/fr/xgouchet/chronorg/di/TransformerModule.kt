package fr.xgouchet.chronorg.di

import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.ui.transformer.ProjectPreviewTransformer
import fr.xgouchet.chronorg.ui.transformer.ProjectsListTransformer
import fr.xgouchet.chronorg.ui.transformer.ViewModelListTransformer
import org.kodein.di.Kodein
import org.kodein.di.generic.bind
import org.kodein.di.generic.provider

val TransformerModule = Kodein.Module(name = "Transformer") {

    bind<ViewModelListTransformer<List<Project>>>() with provider { ProjectsListTransformer() }

    bind<ViewModelListTransformer<Project>>() with provider { ProjectPreviewTransformer() }
}
