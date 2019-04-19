package fr.xgouchet.chronorg.ui.transformer

import fr.xgouchet.chronorg.ui.items.Item

interface ViewModelListTransformer<T> {

    fun transform(entity : T) : List<Item.ViewModel>
}
