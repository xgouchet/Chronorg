package fr.xgouchet.triplea.core.front.mvp

interface BaseEntityContract {

    interface Presenter : BaseContract.Presenter

    interface View<T : Any> : BaseContract.View {
        fun showEntity(entity: T)
    }

}
