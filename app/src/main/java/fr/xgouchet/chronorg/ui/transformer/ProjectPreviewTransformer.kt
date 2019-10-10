package fr.xgouchet.chronorg.ui.transformer

import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.items.ItemDetail
import fr.xgouchet.chronorg.ui.source.asImageSource
import fr.xgouchet.chronorg.ui.source.asTextSource

class ProjectPreviewTransformer
    : ViewModelListTransformer<Project> {
    override fun transform(entity: Project): List<Item.ViewModel> {
        return listOf(
                ItemDetail.ViewModel(
                        index = Item.Index(0, 0),
                        title = "Travelling Entities".asTextSource(),
                        description = "0".asTextSource(),
                        icon = R.drawable.ic_entity.asImageSource()
                ),
                ItemDetail.ViewModel(
                        index = Item.Index(0, 0),
                        title = "Travelling Entities".asTextSource(),
                        description = "0".asTextSource(),
                        icon = R.drawable.ic_entity.asImageSource()
                )
        )
    }
}
