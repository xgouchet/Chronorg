package fr.xgouchet.khronorg.commons.formatters

/**
 * @author Xavier F. Gouchet
 */
interface Formatter<T> {

    fun format(input : T) : String
}