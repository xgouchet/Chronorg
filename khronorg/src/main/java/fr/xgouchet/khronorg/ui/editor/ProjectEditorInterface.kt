package fr.xgouchet.khronorg.ui.editor

import fr.xgouchet.khronorg.data.models.Project
import java.util.*

/**
 * @author Xavier F. Gouchet
 */
class ProjectEditorInterface : EditorInterface<Project> {

    override fun generateItems(content: Project): List<EditorItem> {
        return Collections.singletonList(EditorTextItem("name", content.name))
    }

}