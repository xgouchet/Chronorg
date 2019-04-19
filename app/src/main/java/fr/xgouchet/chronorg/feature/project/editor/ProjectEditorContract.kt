package fr.xgouchet.chronorg.feature.project.editor

import fr.xgouchet.chronorg.android.mvvm.BaseViewModel

interface ProjectEditorContract {

    interface ViewModel : BaseViewModel {
        suspend fun onSave() : Boolean
    }
}
