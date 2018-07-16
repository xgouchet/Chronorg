package fr.xgouchet.triplea.core.front.mvp

interface BaseContract {

    interface Presenter {
        fun onViewAttached(v: View, isRestored: Boolean)
        fun onViewDetached()
        fun getKey(): String
    }

    interface View {
        fun setPresenter(p: Presenter)
        fun showError(throwable: Throwable)
        fun showLoading()
    }

}
