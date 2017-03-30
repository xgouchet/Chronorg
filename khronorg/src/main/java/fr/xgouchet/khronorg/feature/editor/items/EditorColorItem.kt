package fr.xgouchet.khronorg.feature.editor.items

data class EditorColorItem(private val name: String, val hintRes: Int, var color: Int) : EditorItem {
    override val itemType: Int = EditorItem.ITEM_COLOR
    override val itemName: String = name
}