package fr.xgouchet.chronorg.ui.transformer

import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.model.ProjectLink
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
                description = entity.entityCount.toString().asTextSource(),
                icon = R.drawable.ic_entity.asImageSource(),
                data = ProjectLink(entity, ProjectLink.Type.ENTITIES)
            ),
            ItemDetail.ViewModel(
                index = Item.Index(0,1),
                title = "Portals".asTextSource(),
                description = entity.portalCount.toString().asTextSource(),
                icon = R.drawable.ic_portal.asImageSource(),
                data = ProjectLink(entity, ProjectLink.Type.PORTALS)
            ),
            ItemDetail.ViewModel(
                index = Item.Index(0, 2),
                title = "Events".asTextSource(),
                description = entity.eventCount.toString().asTextSource(),
                icon = R.drawable.ic_event.asImageSource(),
                data = ProjectLink(entity, ProjectLink.Type.EVENTS)
            )
        )
    }
}
