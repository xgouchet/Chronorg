package fr.xgouchet.khronorg.feature.editor.items

/**
 * @author Xavier F. Gouchet
 */
interface EditorItem {

    val itemType: Int
    val itemName: String

    companion object {
        val ITEM_TEXT = 1
        val ITEM_COLOR = 2
        val ITEM_INSTANT = 3
    }
}

