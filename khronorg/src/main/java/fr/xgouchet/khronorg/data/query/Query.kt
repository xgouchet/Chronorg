package fr.xgouchet.khronorg.data.query

/**
 * @author Xavier F. Gouchet
 */
interface Query {

    fun select(): String?

    fun args(): Array<out String?>?

    fun order(): String?

}

