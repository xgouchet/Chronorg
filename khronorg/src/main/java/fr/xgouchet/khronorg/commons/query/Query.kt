package fr.xgouchet.khronorg.commons.query

/**
 * @author Xavier F. Gouchet
 */
interface Query {

    fun select(): String?

    fun args(): Array<out String?>?

    fun order(): String?

}


