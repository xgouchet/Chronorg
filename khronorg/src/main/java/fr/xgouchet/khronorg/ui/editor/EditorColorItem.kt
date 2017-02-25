package fr.xgouchet.khronorg.ui.editor

data class EditorColorItem(private val name: String, val hintRes: Int, var color: Int) : EditorItem {
    override val itemType: Int = EditorItem.ITEM_COLOR
    override val itemName: String = name
}