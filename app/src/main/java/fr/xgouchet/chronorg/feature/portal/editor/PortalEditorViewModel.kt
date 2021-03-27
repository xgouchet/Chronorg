package fr.xgouchet.chronorg.feature.portal.editor

import android.app.Activity
import android.content.Intent
import androidx.navigation.NavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.activity.DirectionPickerActivity
import fr.xgouchet.chronorg.android.activity.Request
import fr.xgouchet.chronorg.android.mvvm.SimpleViewModel
import fr.xgouchet.chronorg.data.flow.model.DIRECTION_NONE
import fr.xgouchet.chronorg.data.flow.model.Direction
import fr.xgouchet.chronorg.data.flow.model.Portal
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.data.flow.model.name
import fr.xgouchet.chronorg.data.flow.sink.DataSink
import fr.xgouchet.chronorg.ui.formatter.Formatter
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.items.ItemRawInput
import fr.xgouchet.chronorg.ui.items.ItemTextInput
import fr.xgouchet.chronorg.ui.source.TextSource
import fr.xgouchet.chronorg.ui.source.asImageSource
import fr.xgouchet.chronorg.ui.source.asTextSource
import org.joda.time.Instant
import org.joda.time.Interval

class PortalEditorViewModel(
    private val projectSink: DataSink<Portal>,
    private val intervalFormatter: Formatter<Interval>
) : SimpleViewModel<PortalEditorViewModel, PortalEditorFragment>(),
    PortalEditorContract.ViewModel {

    var project: Project? = null

    private var name = ""
    private var notes = ""
    private var delay: Interval? = null
    private var direction: Direction = DIRECTION_NONE

    override suspend fun getData(): List<Item.ViewModel> {
        return listOf(
            ItemTextInput.ViewModel(
                index = Item.Index(0, 0),
                hint = "Portal name".asTextSource(),
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
                icon = R.drawable.ic_duration.asImageSource(),
                hint = R.string.hint_portal_delay.asTextSource(),
                value = delayTextSource(delay),
                data = ID_DELAY
            ),
            ItemRawInput.ViewModel(
                index = Item.Index(0, 3),
                icon = R.drawable.ic_direction.asImageSource(),
                hint = R.string.hint_portal_direction.asTextSource(),
                value = direction.name().asTextSource(),
                data = ID_DIRECTION
            )
        )
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        val data = event.viewModel.data()
        when (event.action) {
            Item.Action.ITEM_TAPPED -> {
                when (data) {
                    ID_DIRECTION -> fragment?.promptDirection(Request.PICK_DIRECTION, direction)
                    else -> TODO()
                }
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
            Portal(
                id = 0L,
                projectId = project?.id ?: 0L,
                name = name,
                notes = notes,
                delay = Interval(Instant(), Instant()), // TODO
                direction = direction
            )
        )
        return id >= 0
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return
        if (data == null) return

        when (requestCode) {
            Request.PICK_DIRECTION -> direction = data.getIntExtra(
                DirectionPickerActivity.EXTRA_RESULT,
                direction
            )
        }
    }

    private fun delayTextSource(interval: Interval?): TextSource {
        return if (interval == null) {
            "?".asTextSource()
        } else {
            intervalFormatter.format(interval).asTextSource()
        }
    }

    companion object {
        const val ID_NAME = "name"
        const val ID_NOTES = "notes"
        const val ID_DELAY = "delay"
        const val ID_DIRECTION = "direction"
    }
}
