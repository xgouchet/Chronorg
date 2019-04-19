package fr.xgouchet.chronorg.ui.transformer

import fr.xgouchet.chronorg.ui.items.Item

interface ViewModelTransformer<T> {

    fun transform(entity : T) : Item.ViewModel
}
