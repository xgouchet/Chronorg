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
import fr.xgouchet.chronorg.ui.items.ItemAddInput
import fr.xgouchet.chronorg.ui.items.ItemJump
import fr.xgouchet.chronorg.ui.source.asImageSource
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

    var jumpList: List<Jump> = emptyList()

    override suspend fun getData(): List<Item.ViewModel> {
        return withContext(Dispatchers.IO) {
            val entity = entitySource.get(entity.id) ?: return@withContext emptyList()
            val jumps = jumpSource.getAllInParent(entity.id)
            val jumpVMList = jumpListTransformer.transform(jumps)

            val header = listOf(
                ItemJump.ViewModel(
                    index = Item.Index(0, 1),
                    icon = R.drawable.ic_event.asImageSource(),
                    title = R.string.hint_entity_birth.asTextSource(),
                    to = ("* " + instantFormatter.format(entity.birth)).asTextSource()
                ),
                ItemAddInput.ViewModel(
                    index = Item.Index(0,2),
                    data = null
                )
            )
            val footer = listOf<Item.ViewModel>(
                ItemJump.ViewModel(
                    index = Item.Index(0, 3),
                    icon = R.drawable.ic_event.asImageSource(),
                    title = R.string.hint_entity_death.asTextSource(),
                    from = ("† " + instantFormatter.format(entity.death)).asTextSource(),
                )
            )
            jumpList = jumps
            header + jumpVMList + footer
        }
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        if (event.viewModel.type() == Item.Type.ADD_INPUT) {
            val previousJump = event.viewModel.data() as? Jump
            val order = (previousJump?.jumpOrder ?: -1) + 1
            val nextJump = jumpList.firstOrNull { it.jumpOrder == order }
            val bundle = Bundle(4)
            bundle.putParcelable("entity", entity)
            bundle.putLong("order", order)
            bundle.putString("from_after", (previousJump?.to ?: entity.birth).toString())
            bundle.putString("to_before", (nextJump?.from ?: entity.death).toString())
            navController.navigate(R.id.jumpEditorFragment, bundle)
        }
    }

    suspend fun onDelete(): Boolean {
        val entity = entity
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
