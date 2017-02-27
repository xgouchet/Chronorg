package fr.xgouchet.khronorg.ui.viewholders

import android.content.res.ColorStateList
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.view.View
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.formatters.Formatter
import fr.xgouchet.khronorg.ui.Cutelry
import fr.xgouchet.khronorg.ui.editor.EditorColorItem
import fr.xgouchet.khronorg.ui.editor.EditorInstantItem
import io.reactivex.functions.Consumer
import org.joda.time.ReadableInstant

/**
 *
 */
class EditorInstantViewHolder(val formatter: Formatter<ReadableInstant>, listener: Consumer<EditorInstantItem>, view: View)
    : EditorItemViewHolder<EditorInstantItem>(listener, view) {

    val view: TextView by Cutelry.knife(R.id.input_instant)
    val label: TextView by Cutelry.knife(R.id.input_instant_label)

    init {
        view.setOnClickListener { v -> fireSelected() }
    }

    override fun onBindItem(item: EditorInstantItem) {
        label.setText(item.hintRes)
        view.text = formatter.format(item.instant)
    }

}