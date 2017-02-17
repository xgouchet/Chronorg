package fr.xgouchet.khronorg.ui.fragments

import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.ui.editor.EditorInterface
import fr.xgouchet.khronorg.ui.editor.ProjectEditorInterface

/**
 * @author Xavier F. Gouchet
 */
class ProjectEditorFragment : EditorFragment<Project>() {


    override val editorInterface: EditorInterface<Project> = ProjectEditorInterface()
}