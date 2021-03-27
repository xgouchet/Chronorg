package fr.xgouchet.chronorg.feature.portal.list

import android.os.Bundle
import androidx.navigation.NavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.SimpleViewModel
import fr.xgouchet.chronorg.data.flow.model.Portal
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.source.DataSource
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.transformer.ViewModelListTransformer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class PortalListViewModel(
    private val portalSource: DataSource<Portal>,
    private val transformer: ViewModelListTransformer<List<Portal>>
) : SimpleViewModel<PortalListViewModel, PortalListFragment>(), PortalListContract.ViewModel {

    var project: Project? = null

    override suspend fun getData(): List<Item.ViewModel> {
        return withContext(Dispatchers.IO) {
            transformer.transform(portalSource.getAllInParent(project?.id ?: 0))
        }
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        val data = event.viewModel.data() as? Project ?: return
        val bundle = Bundle(1)
        bundle.putParcelable("project", data)
        navController.navigate(R.id.projectPreviewFragment, bundle)
    }
}
