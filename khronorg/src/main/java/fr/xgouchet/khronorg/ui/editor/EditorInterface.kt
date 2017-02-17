package fr.xgouchet.khronorg.ui.editor

/**
 * @author Xavier F. Gouchet
 */
interface EditorInterface<T> {

    fun generateItems(content: T): List<EditorItem>

}