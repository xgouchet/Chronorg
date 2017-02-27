package fr.xgouchet.khronorg.ui.editor

import org.joda.time.Instant
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
data class EditorInstantItem(private val name: String, val hintRes: Int, var instant: ReadableInstant) : EditorItem{
        override val itemType: Int = EditorItem.ITEM_INSTANT
        override val itemName: String = name
}