package fr.xgouchet.chronorg.feature.entity.editor

import android.app.Activity
import android.content.Intent
import androidx.navigation.NavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.activity.InstantPickerActivity
import fr.xgouchet.chronorg.android.activity.Request
import fr.xgouchet.chronorg.android.mvvm.SimpleViewModel
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.sink.DataSink
import fr.xgouchet.chronorg.ui.formatter.Formatter
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.items.ItemRawInput
import fr.xgouchet.chronorg.ui.items.ItemTextInput
import fr.xgouchet.chronorg.ui.source.TextSource
import fr.xgouchet.chronorg.ui.source.asImageSource
import fr.xgouchet.chronorg.ui.source.asTextSource
import org.joda.time.Instant

class EntityEditorViewModel(
    private val entitySink: DataSink<Entity>,
    private val instantFormatter: Formatter<Instant>
) : SimpleViewModel<EntityEditorViewModel, EntityEditorFragment>(),
    EntityEditorContract.ViewModel {

    var project: Project? = null

    private var name = ""
    private var notes = ""
    private var birth: Instant? = null
    private var death: Instant? = null

    override suspend fun getData(): List<Item.ViewModel> {
        return listOf(
            ItemTextInput.ViewModel(
                index = Item.Index(0, 0),
                hint = "Entity name".asTextSource(),
                value = name.asTextSource(),
                data = ID_NAME
            ),
            ItemTextInput.ViewModel(
                index = Item.Index(0, 1),
                hint = "Description".asTextSource(),
                value = notes.asTextSource(),
                data = ID_NOTES
            ),
            ItemRawInput.ViewModel(
                index = Item.Index(0, 2),
                icon = R.drawable.ic_instant.asImageSource(),
                hint = R.string.hint_entity_birth.asTextSource(),
                value = dateViewTextSource(birth),
                data = ID_BIRTH
            ),
            ItemRawInput.ViewModel(
                index = Item.Index(0, 3),
                icon = R.drawable.ic_instant.asImageSource(),
                hint = R.string.hint_entity_death.asTextSource(),
                value = dateViewTextSource(death),
                data = ID_DEATH
            )
        )
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        val data = event.viewModel.data()
        when (event.action) {
            Item.Action.ITEM_TAPPED -> {
                val (request, value) = when (data) {
                    ID_BIRTH -> Request.PICK_BIRTH_DATE to birth
                    ID_DEATH -> Request.PICK_DEATH_DATE to death
                    else -> TODO()
                }
                fragment?.promptInstant(request, value)
            }
            Item.Action.VALUE_CHANGED -> {

                val strValue = (event.value as? String).orEmpty()
                when (data) {
                    ID_NAME -> name = strValue
                    ID_NOTES -> notes = strValue
                }
            }
            else -> {
            }
        }
    }

    override suspend fun onSave(): Boolean {
        val id = entitySink.create(
            Entity(
                id = 0L,
                projectId = project?.id ?: 0L,
                name = name,
                notes = notes,
                birth = Instant(),
                death = Instant()
            )
        )
        return id >= 0
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return
        val instantData = data?.getStringExtra(InstantPickerActivity.EXTRA_RESULT) ?: return
        val instant = Instant(instantData)

        when (requestCode) {
            Request.PICK_BIRTH_DATE -> birth = instant
            Request.PICK_DEATH_DATE -> death = instant
        }
    }

    private fun dateViewTextSource(instant: Instant?): TextSource {
        return if (instant == null) {
            "?".asTextSource()
        } else {
            instantFormatter.format(instant).asTextSource()
        }
    }

    companion object {
        const val ID_NAME = "name"
        const val ID_NOTES = "notes"
        const val ID_BIRTH = "birth"
        const val ID_DEATH = "death"
    }
}