package fr.xgouchet.khronorg.data.formatters

/**
 * @author Xavier F. Gouchet
 */
interface Formatter<T> {

    fun format(input : T) : String
}