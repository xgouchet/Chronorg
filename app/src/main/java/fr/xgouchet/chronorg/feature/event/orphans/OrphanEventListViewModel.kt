package fr.xgouchet.chronorg.feature.event.orphans

import androidx.navigation.NavController
import fr.xgouchet.chronorg.android.mvvm.SimpleViewModel
import fr.xgouchet.chronorg.data.flow.model.Event
import fr.xgouchet.chronorg.data.flow.source.DataSource
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.transformer.ViewModelListTransformer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OrphanEventListViewModel(
    private val eventSource: DataSource<Event>,
    private val transformer: ViewModelListTransformer<List<Event>>
) : SimpleViewModel<OrphanEventListViewModel, OrphanEventListFragment>(), OrphanEventContract.ViewModel {


    override suspend fun getData(): List<Item.ViewModel> {
        return withContext(Dispatchers.IO) {
            transformer.transform(eventSource.getAllOrphans())
        }
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        TODO()
        // val data = event.viewModel.data() as? Project ?: return
        // val bundle = Bundle(1)
        // bundle.putParcelable("project", data)
        // navController.navigate(R.id.projectPreviewFragment, bundle)
    }
}
