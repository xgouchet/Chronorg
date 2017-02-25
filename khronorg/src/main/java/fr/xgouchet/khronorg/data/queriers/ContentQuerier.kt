package fr.xgouchet.khronorg.data.queriers

import android.content.ContentResolver
import fr.xgouchet.khronorg.data.query.QueryAlteration
import io.reactivex.functions.Consumer

/**
 * @author Xavier Gouchet
 */
interface ContentQuerier<T> {

    fun queryAll(contentResolver: ContentResolver, action: Consumer<T>)

    fun queryWhere(contentResolver: ContentResolver, alter: QueryAlteration, action: Consumer<T>)

    fun save(contentResolver: ContentResolver, item: T): Boolean

    fun delete(contentResolver: ContentResolver, item: T): Boolean
}
