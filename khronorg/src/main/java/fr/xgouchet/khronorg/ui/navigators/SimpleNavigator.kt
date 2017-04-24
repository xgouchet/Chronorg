package fr.xgouchet.khronorg.ui.navigators

/**
 * @author Xavier F. Gouchet
 */
class SimpleNavigator<T>(var details: ((T) -> Unit) = {},
                         var edit: ((T) -> Unit) = {},
                         var create: (() -> Unit) = {},
                         var back: (() -> Unit) = {})
    : Navigator<T> {
    override fun goToItemDetails(item: T) {
        details.invoke(item)
    }

    override fun goToItemEdition(item: T) {
        edit.invoke(item)
    }

    override fun goToItemCreation() {
        create.invoke()
    }

    override fun goBack() {
        back.invoke()
    }
}