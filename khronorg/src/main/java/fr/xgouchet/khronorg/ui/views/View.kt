package fr.xgouchet.khronorg.ui.views

/**
 * @author Xavier F. Gouchet
 */
interface View<in T> {

    fun setLoading(isLoading: Boolean = true)

    fun setError(throwable: Throwable)

    fun setEmpty()

    fun setContent(content: T)
}

