package fr.xgouchet.chronorg.feature.jump.editor

import fr.xgouchet.chronorg.android.mvvm.BaseViewModel

interface JumpEditorContract {

    interface ViewModel : BaseViewModel {
        suspend fun onSave() : Boolean
    }
}
