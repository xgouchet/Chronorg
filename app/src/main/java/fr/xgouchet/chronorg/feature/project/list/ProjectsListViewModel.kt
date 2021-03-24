package fr.xgouchet.chronorg.feature.project.list

import android.os.Bundle
import androidx.navigation.NavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.SimpleViewModel
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.sink.DataSink
import fr.xgouchet.chronorg.data.flow.sink.EntitySink
import fr.xgouchet.chronorg.data.flow.source.DataSource
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.transformer.ViewModelListTransformer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.Instant

class ProjectsListViewModel(
    private val projectSource: DataSource<Project>,
    private val projectSink: DataSink<Project>,
    private val entitySink: DataSink<Entity>,
    private val transformer: ViewModelListTransformer<List<Project>>
) : SimpleViewModel<ProjectsListViewModel, ProjectListFragment>(), ProjectListContract.ViewModel {

    override suspend fun getData(): List<Item.ViewModel> {
        return withContext(Dispatchers.IO) {
            transformer.transform(projectSource.getAll())
        }
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        val data = event.viewModel.data() as? Project ?: return
        val bundle = Bundle(1)
        bundle.putParcelable("project", data)
        navController.navigate(R.id.projectPreviewFragment, bundle)
    }

    suspend fun createDemoProject() {
        withContext(Dispatchers.IO) {
            val projectId = projectSink.create(
                Project(
                    0,
                    "Back to the Future",
                    "The movie trilogy by Robert Zemeckis",
                    0,
                    0,
                    0
                )
            )

            val martyId = entitySink.create(
                Entity(
                    0,
                    projectId,
                    "Marty Mc Fly",
                    "",
                    Instant("1968-06-12T10:00-07:00"),
                    Instant("2045-03-03T18:30-07:00")
                )
            )
        }
    }
}
