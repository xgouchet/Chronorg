package fr.xgouchet.khronorg.ui.editor

import fr.xgouchet.khronorg.ui.editor.EditorItem.Companion.ITEM_TEXT

/**
 * @author Xavier F. Gouchet
 */
interface EditorItem {

    val itemType: Int

    companion object {
        val ITEM_TEXT = 1
        val ITEM_COLOR = 2
    }
}

data class EditorTextItem(val name: String, var text: String) : EditorItem {
    override val itemType: Int = ITEM_TEXT
}

data class EditorColorItem(val name: String, var color: Int) : EditorItem {
    override val itemType: Int = ITEM_TEXT
}