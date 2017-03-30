package fr.xgouchet.khronorg.feature.editor.viewholders

import android.content.res.ColorStateList
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.M
import android.view.View
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.ui.Cutelry
import fr.xgouchet.khronorg.feature.editor.items.EditorColorItem
import io.reactivex.functions.Consumer

/**
 *
 */
class EditorColorViewHolder(listener: Consumer<EditorColorItem>, view: View)
    : EditorItemViewHolder<EditorColorItem>(listener, view) {

    val view: TextView by Cutelry.knife(R.id.input_color)

    init {
        view.setOnClickListener { v -> fireSelected() }
    }

    override fun onBindItem(item: EditorColorItem) {
        view.setTextColor(item.color)
        if (SDK_INT >= M) {
            view.compoundDrawableTintList = ColorStateList.valueOf(item.color)
        }
    }

}