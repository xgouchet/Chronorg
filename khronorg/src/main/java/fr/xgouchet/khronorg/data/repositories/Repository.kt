package fr.xgouchet.khronorg.data.repositories

import io.reactivex.Observable
import io.reactivex.internal.operators.observable.ObservableAll

/**
 * @author Xavier F. Gouchet
 */
interface Repository<I, T> {

    fun get(input : I) : Observable<T>

}