package fr.xgouchet.khronorg.feature.editor.items

import fr.xgouchet.khronorg.feature.portals.Portal
import fr.xgouchet.khronorg.feature.projects.Project

/**
 * @author Xavier F. Gouchet
 */
data class EditorPortalItem(private val name: String,
                            val hintRes: Int,
                            val project: Project,
                            var portal: Portal? = null)
    : EditorItem {
    override val itemType: Int = EditorItem.ITEM_PORTAL
    override val itemName: String = name
}