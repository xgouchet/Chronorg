package fr.xgouchet.chronorg.feature.entity.editor

import android.app.Activity
import android.content.Intent
import androidx.annotation.StringRes
import androidx.navigation.NavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.activity.InstantPickerActivity
import fr.xgouchet.chronorg.android.mvvm.SimpleViewModel
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.sink.DataSink
import fr.xgouchet.chronorg.ui.formatter.Formatter
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.items.ItemInstantInput
import fr.xgouchet.chronorg.ui.items.ItemTextInput
import fr.xgouchet.chronorg.ui.source.TextSource
import fr.xgouchet.chronorg.ui.source.asTextSource
import org.joda.time.Instant

class EntityEditorViewModel(
    private val projectSink: DataSink<Entity>,
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
            ItemInstantInput.ViewModel(
                index = Item.Index(0, 2),
                value = dateViewTextSource(R.string.hint_entity_birth, birth),
                data = ID_BIRTH
            ),
            ItemInstantInput.ViewModel(
                index = Item.Index(0, 3),
                value = dateViewTextSource(R.string.hint_entity_death, death),
                data = ID_DEATH
            )
        )
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        val data = event.viewModel.data()
        when (event.action) {
            Item.Action.ITEM_TAPPED -> {
                val (request, value) = when (data) {
                    ID_BIRTH -> REQUEST_BIRTH_DATE to birth
                    ID_DEATH -> REQUEST_DEATH_DATE to death
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
        val id = projectSink.create(
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
            REQUEST_BIRTH_DATE -> birth = instant
            REQUEST_DEATH_DATE -> death = instant
        }
    }

    private fun dateViewTextSource(@StringRes hint: Int, instant: Instant?): TextSource {
        val hintSource = hint.asTextSource()
        val valueSource = if (instant == null) {
            ": ?".asTextSource()
        } else {
            ": ${instantFormatter.format(instant)}".asTextSource()
        }
        return hintSource + valueSource
    }

    companion object {
        const val ID_NAME = "name"
        const val ID_NOTES = "notes"
        const val ID_BIRTH = "birth"
        const val ID_DEATH = "death"

        private const val REQUEST_BIRTH_DATE = 42
        private const val REQUEST_DEATH_DATE = 666
    }
}
