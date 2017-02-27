package fr.xgouchet.khronorg.ui.presenters

interface EditorPresenter<T> : Presenter<T> {

    fun applyEdition()

    val isDeletable: Boolean

}