package fr.xgouchet.chronorg.feature.entity.timeline

import android.os.Bundle
import androidx.navigation.NavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.SimpleViewModel
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Jump
import fr.xgouchet.chronorg.data.flow.sink.DataSink
import fr.xgouchet.chronorg.data.flow.source.DataSource
import fr.xgouchet.chronorg.ui.formatter.Formatter
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.items.ItemDetail
import fr.xgouchet.chronorg.ui.items.ItemJump
import fr.xgouchet.chronorg.ui.source.asTextSource
import fr.xgouchet.chronorg.ui.transformer.ViewModelListTransformer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.joda.time.Instant

class EntityTimelineViewModel(
    private val entitySource: DataSource<Entity>,
    private val jumpSource: DataSource<Jump>,
    private val entitySink: DataSink<Entity>,
    private val instantFormatter: Formatter<Instant>,
    private val jumpListTransformer: ViewModelListTransformer<List<Jump>>
) : SimpleViewModel<EntityTimelineViewModel, EntityTimelineFragment>(),
    EntityTimelineContract.ViewModel {

    lateinit var entity: Entity

    override suspend fun getData(): List<Item.ViewModel> {
        return withContext(Dispatchers.IO) {
            val entity = entitySource.get(entity.id) ?: return@withContext emptyList()
            val jumps = jumpSource.getAllInParent(entity.id)
            val jumpVMList = jumpListTransformer.transform(jumps)

            val header = listOf(
                ItemDetail.ViewModel(
                    index = Item.Index(0, 0),
                    title = entity.name.asTextSource(),
                    description = entity.notes.asTextSource()
                ),
                ItemJump.ViewModel(
                    index = Item.Index(0, 0),
                    title = R.string.hint_entity_birth.asTextSource(),
                    from = "".asTextSource(),
                    to = ("* " + instantFormatter.format(entity.birth)).asTextSource()
                )
            )
            val footer = listOf<Item.ViewModel>(
                ItemJump.ViewModel(
                    index = Item.Index(0, 0),
                    title = R.string.hint_entity_death.asTextSource(),
                    from = ("† " + instantFormatter.format(entity.death)).asTextSource(),
                    to = "".asTextSource()
                )
            )

            header + jumpVMList + footer
        }
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        TODO()
        // val data = event.viewModel.data() as? ProjectLink ?: return
        // val bundle = Bundle(1)
        // bundle.putParcelable("project", data.project)
        // val target = when (data.link) {
        //     ProjectLink.Type.ENTITIES -> R.id.entityListFragment
        //     ProjectLink.Type.PORTALS -> R.id.portalListFragment
        //     ProjectLink.Type.EVENTS -> R.id.eventListFragment
        // }
        // navController.navigate(target, bundle)
    }

    suspend fun onDelete(): Boolean {
        val entity = entity ?: return false
        return withContext(Dispatchers.IO) {
            entitySink.delete(entity)
        }
    }

    fun onEdit(navController: NavController) {
        val entity = entity ?: return
        val bundle = Bundle(1)
        bundle.putParcelable("entity", entity)

        navController.navigate(R.id.entityEditorFragment, bundle)
    }
}
