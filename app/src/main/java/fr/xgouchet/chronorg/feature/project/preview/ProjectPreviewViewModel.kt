package fr.xgouchet.chronorg.feature.project.preview

import android.os.Bundle
import androidx.navigation.NavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.SimpleViewModel
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.model.ProjectLink
import fr.xgouchet.chronorg.data.flow.sink.DataSink
import fr.xgouchet.chronorg.data.flow.source.DataSource
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.transformer.ViewModelListTransformer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProjectPreviewViewModel(
    private val projectSource: DataSource<Project>,
    private val projectSink: DataSink<Project>,
    private val transformer: ViewModelListTransformer<Project>
) : SimpleViewModel<ProjectPreviewViewModel, ProjectPreviewFragment>(),
    ProjectPreviewContract.ViewModel {

    var project: Project? = null

    override suspend fun getData(): List<Item.ViewModel> {
        return withContext(Dispatchers.IO) {
            val entity = projectSource.get(project?.id ?: 0)
            if (entity == null) emptyList() else transformer.transform(entity)
        }
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        val data = event.viewModel.data() as? ProjectLink ?: return
        val bundle = Bundle(1)
        bundle.putParcelable("project", data.project)
        val target = when (data.link) {
            ProjectLink.Type.ENTITIES -> R.id.action_listEntities
            ProjectLink.Type.PORTALS -> TODO()
            ProjectLink.Type.EVENTS -> TODO()
        }
        navController.navigate(target, bundle)
    }

    suspend fun onDelete(): Boolean {
        val entity = project ?: return false
        return withContext(Dispatchers.IO) {
            projectSink.delete(entity)
        }
    }
}
