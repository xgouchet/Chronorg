package fr.xgouchet.khronorg.data.repositories

import fr.xgouchet.khronorg.data.query.QueryAlteration
import io.reactivex.Observable

/**
 * @author Xavier F. Gouchet
 */
interface Repository<T> {

    fun getAll(): Observable<T>

    fun getWhere(alter: QueryAlteration): Observable<T>

    fun save(item: T): Observable<Any>

    fun current(): Observable<T>

    fun setCurrent(item: T)

    fun getCurrent(): T

}

