package fr.xgouchet.chronorg.feature.project.preview

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.source.DataSource
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.transformer.ViewModelListTransformer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectPreviewViewModel(
        private val projectSource: DataSource<Project>,
        private val transformer: ViewModelListTransformer<Project>
) : ViewModel(),
        ProjectPreviewContract.ViewModel {

    override suspend fun getData(): List<Item.ViewModel> {
        return withContext(Dispatchers.IO) {
            val entity = projectSource.get(0)
            if (entity == null) emptyList() else transformer.transform(entity)
        }
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {}
}
