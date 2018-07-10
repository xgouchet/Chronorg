package fr.xgouchet.triplea.core.front.mvp

interface BaseListContract {

    interface Presenter<T : Any> : BaseContract.Presenter {
        fun onEntitySelected(entity: T)
    }

    interface View<T : Any> : BaseContract.View {
        fun showEntities(entities: List<T>)
        fun navigateToEntity(entity: T)
    }

}
