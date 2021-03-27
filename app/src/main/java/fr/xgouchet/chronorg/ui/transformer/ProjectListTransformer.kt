package fr.xgouchet.chronorg.ui.transformer

import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.items.ItemCard
import fr.xgouchet.chronorg.ui.items.ItemEmpty
import fr.xgouchet.chronorg.ui.source.asImageSource
import fr.xgouchet.chronorg.ui.source.asTextSource

class ProjectListTransformer
    : PrincipledTransformer<Project>() {

    override fun empty(): Collection<Item.ViewModel> {
        return listOf(
            ItemEmpty.ViewModel(
                icon = R.drawable.ic_project_empty.asImageSource(),
                title = "Projects".asTextSource(),
                subtitle = "It seems there are no projects yet.".asTextSource()
            )
        )
    }

    override fun transformItem(index: Int, item: Project): Collection<Item.ViewModel> {
        return listOf(
            ItemCard.ViewModel(
                index = Item.Index(1, index),
                icon = R.drawable.ic_project.asImageSource(),
                title = item.name.asTextSource(),
                description = item.description.asTextSource(),
                data = item
            )
        )
    }
}


