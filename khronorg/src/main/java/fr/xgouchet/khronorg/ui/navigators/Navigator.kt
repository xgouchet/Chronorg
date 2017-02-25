package fr.xgouchet.khronorg.ui.navigators

/**
 * @author Xavier F. Gouchet
 */
interface Navigator<in T> {

    fun goToItemDetails(item: T)

    fun goToItemEdition(item: T)

    fun goToItemCreation()

    fun goBack()
}