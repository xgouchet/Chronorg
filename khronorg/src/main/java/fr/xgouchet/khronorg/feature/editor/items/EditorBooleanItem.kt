package fr.xgouchet.khronorg.feature.editor.items

/**
 * @author Xavier F. Gouchet
 */
data class EditorBooleanItem(private val name: String, val hintRes: Int, var value: Boolean) : EditorItem {
        override val itemType: Int = EditorItem.ITEM_BOOLEAN
        override val itemName: String = name
}