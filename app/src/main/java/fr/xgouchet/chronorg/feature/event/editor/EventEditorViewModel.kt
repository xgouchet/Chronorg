package fr.xgouchet.chronorg.feature.event.editor

import android.app.Activity
import android.content.Intent
import androidx.navigation.NavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.activity.InstantPickerActivity
import fr.xgouchet.chronorg.android.activity.Request
import fr.xgouchet.chronorg.android.mvvm.SimpleViewModel
import fr.xgouchet.chronorg.data.flow.model.Event
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

class EventEditorViewModel(
    private val eventSink: DataSink<Event>,
    private val instantFormatter: Formatter<Instant>
) : SimpleViewModel<EventEditorViewModel, EventEditorFragment>(),
    EventEditorContract.ViewModel {

    lateinit var project: Project

    private var name = ""
    private var notes = ""
    private var date: Instant? = null

    override suspend fun getData(): List<Item.ViewModel> {
        return listOf(
            ItemTextInput.ViewModel(
                index = Item.Index(0, 0),
                hint = "Event name".asTextSource(),
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
                hint = R.string.hint_event_instant.asTextSource(),
                value = dateViewTextSource(date),
                data = ID_DATE
            )
        )
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        val data = event.viewModel.data()
        when (event.action) {
            Item.Action.ITEM_TAPPED -> {
                val (request, value) = when (data) {
                    ID_DATE -> Request.PICK_EVENT_DATE to date
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
        val id = eventSink.create(
            Event(
                id = 0L,
                project = project,
                name = name,
                notes = notes,
                date = date ?: Instant()
            )
        )
        return id >= 0
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return
        val instantData = data?.getStringExtra(InstantPickerActivity.EXTRA_RESULT) ?: return
        val instant = Instant(instantData)

        when (requestCode) {
            Request.PICK_EVENT_DATE -> date = instant
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
        const val ID_DATE = "birth"
    }
}
