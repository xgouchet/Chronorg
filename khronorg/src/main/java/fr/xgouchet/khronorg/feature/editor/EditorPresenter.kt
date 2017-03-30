package fr.xgouchet.khronorg.feature.editor

import fr.xgouchet.khronorg.ui.presenters.Presenter
import io.reactivex.Observable

interface EditorPresenter<T> : Presenter<T> {

    fun applyEdition()

    fun delete()

    val isDeletable: Boolean

}