package fr.xgouchet.khronorg.feature.editor.viewholders

import android.view.View
import fr.xgouchet.khronorg.feature.editor.items.EditorItem
import fr.xgouchet.khronorg.ui.viewholders.BaseViewHolder
import io.reactivex.functions.Consumer

/**
 * @author Xavier F. Gouchet
 */
abstract class EditorItemViewHolder<T : EditorItem>(listener: Consumer<T>?, itemView: View)
    : BaseViewHolder<T>(listener, itemView)


