package fr.xgouchet.triplea.core.front.mvp

interface BaseContract {

    interface Presenter {
        fun onViewAttached(v: View, isRestored: Boolean)
        fun onViewDetached()
    }

    interface View {
        fun setPresenter(p: Presenter)
        fun showError(throwable: Throwable)
        fun showLoading()
    }

}
