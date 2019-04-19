package fr.xgouchet.chronorg.feature.project.editor

import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.sink.DataSink
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.items.ItemTextInput
import fr.xgouchet.chronorg.ui.source.asTextSource

class ProjectEditorViewModel(
        private val projectSink: DataSink<Project>
) : ViewModel(),
        ProjectEditorContract.ViewModel {

    private var name = ""
    private var description = ""

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
        return projectSink.create(Project(id = 0, name = name, description = description))
    }

    companion object {
        const val ID_NAME = "name"
        const val ID_DESC = "description"
    }
}
