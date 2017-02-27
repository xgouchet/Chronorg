package fr.xgouchet.khronorg.ui.adapters

import fr.xgouchet.khronorg.ui.editor.EditorColorItem
import fr.xgouchet.khronorg.ui.editor.EditorInstantItem

/**
 * @author Xavier F. Gouchet
 */
interface EditorAdapterListener {

    fun pickColor(colorItem: EditorColorItem?)

    fun pickInstant(instantItem: EditorInstantItem?)

}