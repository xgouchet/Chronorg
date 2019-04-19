package fr.xgouchet.chronorg.feature.project.list

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.source.DataSource
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.transformer.ViewModelListTransformer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectsListViewModel(
        private val projectSource: DataSource<Project>,
        private val transformer: ViewModelListTransformer<List<Project>>
) : ViewModel(),
        ProjectListContract.ViewModel {

    override suspend fun getData(): List<Item.ViewModel> {
        return withContext(Dispatchers.IO) {
            transformer.transform(projectSource.getAll())
        }
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        val data = event.viewModel.data() as? Project ?: return
        navController.navigate(R.id.projectPreviewFragment)
    }
}
