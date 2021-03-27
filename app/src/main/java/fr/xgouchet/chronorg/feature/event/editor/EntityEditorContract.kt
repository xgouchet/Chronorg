package fr.xgouchet.chronorg.feature.event.editor

import fr.xgouchet.chronorg.android.mvvm.BaseViewModel

interface EventEditorContract {

    interface ViewModel : BaseViewModel {
        suspend fun onSave() : Boolean
    }
}
