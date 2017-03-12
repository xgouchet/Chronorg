package fr.xgouchet.khronorg.ui.presenters

import io.reactivex.Observable

interface EditorPresenter<T> : Presenter<T> {

    fun applyEdition()

    fun delete()

    val isDeletable: Boolean

}