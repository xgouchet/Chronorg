package fr.xgouchet.khronorg.ui.viewholders

import android.view.View
import fr.xgouchet.khronorg.ui.editor.EditorItem
import io.reactivex.functions.Consumer

/**
 * @author Xavier F. Gouchet
 */
abstract class EditorItemViewHolder<T : EditorItem>(listener: Consumer<T>?, itemView: View)
    : BaseViewHolder<T>(listener, itemView)


