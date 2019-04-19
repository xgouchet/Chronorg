package fr.xgouchet.chronorg.ui.transformer

import fr.xgouchet.chronorg.ui.items.Item

open class PrincipledTransformer<T>
    : ViewModelListTransformer<List<T>> {
    override fun transform(entity: List<T>): List<Item.ViewModel> {
        val result = mutableListOf<Item.ViewModel>()

        if (entity.isEmpty()) {
            result.addAll(empty())
        } else {
            result.addAll(headers(entity))

            entity.forEachIndexed { index, item ->
                result.addAll(transformItem(index, item))
            }

            result.addAll(footers(entity))
        }

        return result
    }


    // region Open/Abstract

    open fun empty(): Collection<Item.ViewModel> = emptyList()

    open fun headers(appModel: List<T>): Collection<Item.ViewModel> = emptyList()

    open fun transformItem(index: Int, item: T): Collection<Item.ViewModel> = emptyList()

    open fun footers(appModel: List<T>): Collection<Item.ViewModel> = emptyList()

    // endregion
}
