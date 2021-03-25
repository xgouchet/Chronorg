package fr.xgouchet.chronorg.feature.project.editor

import androidx.navigation.NavController
import fr.xgouchet.chronorg.android.mvvm.SimpleViewModel
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.sink.DataSink
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.items.ItemTextInput
import fr.xgouchet.chronorg.ui.source.asTextSource

class ProjectEditorViewModel(
    private val projectSink: DataSink<Project>
) : SimpleViewModel<ProjectEditorViewModel, ProjectEditorFragment>(),
    ProjectEditorContract.ViewModel {

    internal var projectId: Long? = null
    internal var name = ""
    internal var description = ""

    override suspend fun getData(): List<Item.ViewModel> {
        return listOf(
            ItemTextInput.ViewModel(
                index = Item.Index(0, 0),
                hint = "Project name".asTextSource(),
                value = name.asTextSource(),
                data = ID_NAME
            ),
            ItemTextInput.ViewModel(
                index = Item.Index(0, 1),
                hint = "Description".asTextSource(),
                value = description.asTextSource(),
                data = ID_DESC
            )

        )
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        val data = event.viewModel.data()
        val strValue = (event.value as? String).orEmpty()
        when (data) {
            ID_NAME -> name = strValue
            ID_DESC -> description = strValue
        }
    }

    override suspend fun onSave(): Boolean {
        val project = Project(
            id = projectId ?: 0,
            name = name,
            description = description,
            entityCount = 0,
            portalCount = 0,
            eventCount = 0
        )

        return if (project.id == 0L) {
            projectSink.create(project) >= 0
        } else {
            projectSink.update(project)
        }
    }

    companion object {
        const val ID_NAME = "name"
        const val ID_DESC = "description"
    }
}
