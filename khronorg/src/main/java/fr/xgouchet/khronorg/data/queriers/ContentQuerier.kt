package fr.xgouchet.khronorg.data.queriers

import android.content.ContentResolver
import io.reactivex.functions.Consumer

/**
 * @author Xavier Gouchet
 */
interface ContentQuerier<T> {

    fun queryAll(contentResolver: ContentResolver, action: Consumer<T>)

    fun query(contentResolver: ContentResolver, action: Consumer<T>, id: Int)

    fun save(contentResolver: ContentResolver, item: T): Boolean

    fun delete(contentResolver: ContentResolver, item: T): Boolean
}
