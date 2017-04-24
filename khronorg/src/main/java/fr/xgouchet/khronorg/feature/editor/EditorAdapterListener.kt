package fr.xgouchet.khronorg.feature.editor

import fr.xgouchet.khronorg.feature.editor.items.EditorColorItem
import fr.xgouchet.khronorg.feature.editor.items.EditorInstantItem
import fr.xgouchet.khronorg.feature.editor.items.EditorPortalItem

/**
 * @author Xavier F. Gouchet
 */
interface EditorAdapterListener {

    fun pickColor(colorItem: EditorColorItem?)

    fun pickInstant(instantItem: EditorInstantItem?)

    fun pickPortal(portalItem: EditorPortalItem?)

}