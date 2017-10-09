package fr.xgouchet.khronorg.feature.editor.viewholders

import android.support.v7.widget.SwitchCompat
import android.view.View
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.feature.editor.items.EditorBooleanItem
import fr.xgouchet.khronorg.ui.Cutelry
import io.reactivex.functions.Consumer
import org.joda.time.ReadableInterval

/**
 *
 */
class EditorBooleanViewHolder(val formatter: Formatter<ReadableInterval>, listener: Consumer<EditorBooleanItem>, view: View)
    : EditorItemViewHolder<EditorBooleanItem>(listener, view) {

    val switch: SwitchCompat by Cutelry.knife(R.id.input_boolean)

    init {
        switch.setOnCheckedChangeListener { buttonView, isChecked ->
            item?.value = isChecked
            fireSelected()
        }
    }

    override fun onBindItem(item: EditorBooleanItem) {

        switch.setText(item.hintRes)

        switch.isChecked = item.value
    }

}