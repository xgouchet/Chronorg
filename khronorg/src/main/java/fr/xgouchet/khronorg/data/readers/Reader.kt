package fr.xgouchet.khronorg.data.readers

/**
 * @author Xavier F. Gouchet
 */
interface Reader<T> {

    fun instantiate() : T

    fun fill(data : T)

    fun instantiateAndFill() : T
}