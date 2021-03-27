package fr.xgouchet.chronorg.feature.portal.editor

import fr.xgouchet.chronorg.android.mvvm.BaseViewModel

interface PortalEditorContract {

    interface ViewModel : BaseViewModel {
        suspend fun onSave() : Boolean
    }
}
