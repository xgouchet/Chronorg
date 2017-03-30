package fr.xgouchet.khronorg.commons.queriers

import android.content.ContentResolver
import fr.xgouchet.khronorg.commons.query.Query
import io.reactivex.functions.Consumer

/**
 * @author Xavier Gouchet
 */
interface ContentQuerier<T> {

    fun queryAll(contentResolver: ContentResolver, action: Consumer<T>)

    fun queryWhere(contentResolver: ContentResolver, alter: Query, action: Consumer<T>)

    fun save(contentResolver: ContentResolver, item: T): Boolean

    fun delete(contentResolver: ContentResolver, item: T): Boolean
}
