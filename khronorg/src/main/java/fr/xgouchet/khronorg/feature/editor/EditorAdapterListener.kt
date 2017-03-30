package fr.xgouchet.khronorg.feature.editor

import fr.xgouchet.khronorg.feature.editor.items.EditorColorItem
import fr.xgouchet.khronorg.feature.editor.items.EditorInstantItem

/**
 * @author Xavier F. Gouchet
 */
interface EditorAdapterListener {

    fun pickColor(colorItem: EditorColorItem?)

    fun pickInstant(instantItem: EditorInstantItem?)

}