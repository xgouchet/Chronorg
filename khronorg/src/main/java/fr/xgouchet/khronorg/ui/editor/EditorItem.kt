package fr.xgouchet.khronorg.ui.editor

import fr.xgouchet.khronorg.ui.editor.EditorItem.Companion.ITEM_TEXT

/**
 * @author Xavier F. Gouchet
 */
interface EditorItem {

    val itemType: Int
    val itemName: String

    companion object {
        val ITEM_TEXT = 1
        val ITEM_COLOR = 2
    }
}

data class EditorTextItem(private val name: String, val hintRes: Int, var text: String) : EditorItem {
    override val itemType: Int = ITEM_TEXT
    override val itemName: String = name
}

data class EditorColorItem(private val name: String, var color: Int) : EditorItem {
    override val itemType: Int = ITEM_TEXT
    override val itemName: String = name
}