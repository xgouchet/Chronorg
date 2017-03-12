package fr.xgouchet.khronorg.data.repositories

import fr.xgouchet.khronorg.data.query.Query
import io.reactivex.Observable

/**
 * @author Xavier F. Gouchet
 */
interface Repository<T> {

    fun getAll(): Observable<T>

    fun getWhere(query: Query): Observable<T>

    fun save(item: T): Observable<Any>

    fun delete(item: T): Observable<Any>

    fun deleteWhere(query: Query): Observable<Any>

    fun current(): Observable<T>

    fun setCurrent(item: T)

    fun getCurrent(): T

}

