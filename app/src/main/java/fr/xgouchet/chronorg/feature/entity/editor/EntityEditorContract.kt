package fr.xgouchet.chronorg.feature.entity.editor

import fr.xgouchet.chronorg.android.mvvm.BaseViewModel

interface EntityEditorContract {

    interface ViewModel : BaseViewModel {
        suspend fun onSave() : Boolean
    }
}
