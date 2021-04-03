package fr.xgouchet.chronorg.feature.entity.list

import android.os.Bundle
import androidx.navigation.NavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.SimpleViewModel
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.source.DataSource
import fr.xgouchet.chronorg.feature.entity.orphans.OrphanEntityContract
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.transformer.ViewModelListTransformer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class EntityListViewModel(
    private val entitySource: DataSource<Entity>,
    private val transformer: ViewModelListTransformer<List<Entity>>
) : SimpleViewModel<EntityListViewModel, EntityListFragment>(), OrphanEntityContract.ViewModel {

    lateinit var project: Project

    override suspend fun getData(): List<Item.ViewModel> {
        return withContext(Dispatchers.IO) {
            transformer.transform(entitySource.getAllInParent(project.id))
        }
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        val data = event.viewModel.data() as? Entity ?: return
        val bundle = Bundle(1)
        bundle.putParcelable("entity", data)
        navController.navigate(R.id.entityTimelineFragment, bundle)
    }
}
