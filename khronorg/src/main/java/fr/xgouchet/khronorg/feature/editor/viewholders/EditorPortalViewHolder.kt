package fr.xgouchet.khronorg.feature.editor.viewholders

import android.view.View
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.Formatter
import fr.xgouchet.khronorg.data.models.Direction
import fr.xgouchet.khronorg.feature.editor.items.EditorPortalItem
import fr.xgouchet.khronorg.ui.Cutelry
import io.reactivex.functions.Consumer
import org.joda.time.ReadableInterval

/**
 *
 */
class EditorPortalViewHolder(val formatter: Formatter<ReadableInterval>, listener: Consumer<EditorPortalItem>, view: View)
    : EditorItemViewHolder<EditorPortalItem>(listener, view) {

    val view: TextView by Cutelry.knife(R.id.input_portal)
    val label: TextView by Cutelry.knife(R.id.input_portal_label)

    init {
        view.setOnClickListener { v -> fireSelected() }
    }

    override fun onBindItem(item: EditorPortalItem) {
        label.setText(item.hintRes)

        view.text = "? (?)"

        item.portal?.let {
            val dir: String
            when (it.direction) {
                Direction.FUTURE -> dir = "→"
                Direction.PAST -> dir = "←"
                else -> dir = "?"
            }
            view.text = "${it.name} (${formatter.format(it.delay)} $dir)"
        }
    }

}