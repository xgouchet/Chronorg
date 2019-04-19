package fr.xgouchet.chronorg.ui.transformer

import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.ui.items.Item

class ProjectPreviewTransformer
    : ViewModelListTransformer<Project> {
    override fun transform(entity: Project): List<Item.ViewModel> {
        return emptyList()
    }
}
