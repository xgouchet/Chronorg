package fr.xgouchet.chronorg.feature.jump.editor

import android.app.Activity
import android.content.Intent
import androidx.navigation.NavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.activity.InstantPickerActivity
import fr.xgouchet.chronorg.android.activity.Request
import fr.xgouchet.chronorg.android.mvvm.SimpleViewModel
import fr.xgouchet.chronorg.data.flow.model.Entity
import fr.xgouchet.chronorg.data.flow.model.Jump
import fr.xgouchet.chronorg.data.flow.model.Portal
import fr.xgouchet.chronorg.data.flow.sink.DataSink
import fr.xgouchet.chronorg.ui.formatter.Formatter
import fr.xgouchet.chronorg.ui.items.Item
import fr.xgouchet.chronorg.ui.items.ItemRawInput
import fr.xgouchet.chronorg.ui.items.ItemTextInput
import fr.xgouchet.chronorg.ui.source.TextSource
import fr.xgouchet.chronorg.ui.source.asImageSource
import fr.xgouchet.chronorg.ui.source.asTextSource
import org.joda.time.Instant

class JumpEditorViewModel(
    private val jumpSink: DataSink<Jump>,
    private val instantFormatter: Formatter<Instant>
) : SimpleViewModel<JumpEditorViewModel, JumpEditorFragment>(),
    JumpEditorContract.ViewModel {

    lateinit var entity: Entity
    lateinit var fromAfter: Instant
    lateinit var toBefore: Instant

    private var name = ""
    private var from: Instant? = null
    private var to: Instant? = null
    private var portal: Portal? = null
    internal var jumpOrder: Long = 0


    override suspend fun getData(): List<Item.ViewModel> {
        return listOf(
            ItemTextInput.ViewModel(
                index = Item.Index(0, 0),
                hint = "Jump name".asTextSource(),
                value = name.asTextSource(),
                data = ID_NAME
            ),
            ItemRawInput.ViewModel(
                index = Item.Index(0, 2),
                icon = R.drawable.ic_instant.asImageSource(),
                hint = R.string.hint_jump_from.asTextSource(),
                value = dateViewTextSource(from),
                data = ID_FROM
            ),
            ItemRawInput.ViewModel(
                index = Item.Index(0, 3),
                icon = R.drawable.ic_instant.asImageSource(),
                hint = R.string.hint_jump_to.asTextSource(),
                value = dateViewTextSource(to),
                data = ID_TO
            ),
            ItemRawInput.ViewModel(
                index = Item.Index(0, 4),
                icon = R.drawable.ic_portal.asImageSource(),
                hint = R.string.hint_jump_through_portal.asTextSource(),
                value = portal?.name.orEmpty().asTextSource(),
                data = ID_PORTAL
            )
        )
    }

    override suspend fun onViewEvent(event: Item.Event, navController: NavController) {
        val data = event.viewModel.data()
        when (event.action) {
            Item.Action.ITEM_TAPPED -> {
                if (data == ID_FROM){
                    fragment?.promptInstant(Request.PICK_JUMP_FROM_DATE, from, fromAfter, null)
                } else if (data == ID_TO){
                    fragment?.promptInstant(Request.PICK_JUMP_TO_DATE, to, null, toBefore)
                }
            }
            Item.Action.VALUE_CHANGED -> {

                val strValue = (event.value as? String).orEmpty()
                when (data) {
                    ID_NAME -> name = strValue
                }
            }
            else -> {
            }
        }
    }

    override suspend fun onSave(): Boolean {
        val id = jumpSink.create(
            Jump(
                id = 0L,
                entity = entity,
                name = name,
                from = from ?: Instant(),
                to = to ?: Instant(),
                portal = portal,
                jumpOrder = jumpOrder
            )
        )
        return id >= 0
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) return
        val instantData = data?.getStringExtra(InstantPickerActivity.EXTRA_RESULT) ?: return
        val instant = Instant(instantData)

        when (requestCode) {
            Request.PICK_JUMP_FROM_DATE -> from = instant
            Request.PICK_JUMP_TO_DATE -> to = instant
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
        const val ID_FROM = "from"
        const val ID_TO = "to"
        const val ID_PORTAL = "portal"
    }
}
