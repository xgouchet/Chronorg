package fr.xgouchet.khronorg.feature.editor.items

// TODO add some constraints on allowed values
data class EditorTextItem(private val name: String,
                          val hintRes: Int,
                          var text: String) : EditorItem {
    override val itemType: Int = EditorItem.ITEM_TEXT
    override val itemName: String = name
}