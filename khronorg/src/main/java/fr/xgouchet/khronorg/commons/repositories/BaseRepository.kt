package fr.xgouchet.khronorg.commons.repositories

import android.content.Context
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import fr.xgouchet.khronorg.commons.query.Query
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Consumer
import io.reactivex.subjects.BehaviorSubject

/**
 * @author Xavier F. Gouchet
 */
class BaseRepository<T>(val context: Context, val provider: IOProvider<T>) : Repository<T> {

    private val currentItemSource = BehaviorSubject.create<T>()

    override fun current(): Observable<T> {
        return currentItemSource
    }

    override fun setCurrent(item: T) {
        currentItemSource.onNext(item)
    }

    override fun getCurrent(): T {
        return currentItemSource.value
    }

    override fun getAll(): Observable<T> {
        val source = ObservableOnSubscribe<T> {
            emitter ->
            try {
                val contentResolver = context.contentResolver
                provider.querier.queryAll(contentResolver, Consumer { t -> emitter.onNext(t) })
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }

        return Observable.create(source)
    }


    override fun getWhere(query: Query): Observable<T> {
        val source = ObservableOnSubscribe<T> {
            emitter ->
            try {
                val contentResolver = context.contentResolver
                provider.querier.queryWhere(contentResolver, query, Consumer { t -> emitter.onNext(t) })
                emitter.onComplete()
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }

        return Observable.create(source)
    }

    override fun save(item: T): Observable<Any> {
        val source = ObservableOnSubscribe<Any> {
            emitter ->
            try {
                val contentResolver = context.contentResolver
                if (provider.querier.save(contentResolver, item)) {
                    emitter.onComplete()
                } else {
                    emitter.onError(RuntimeException("Can't save… WTF‽"))
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }

        return Observable.create(source)
    }

    override fun delete(item: T): Observable<Any> {
        val source = ObservableOnSubscribe<Any> {
            emitter ->
            try {
                val contentResolver = context.contentResolver
                if (provider.querier.delete(contentResolver, item)) {
                    emitter.onComplete()
                } else {
                    emitter.onError(RuntimeException("Can't delete… WTF‽"))
                }
            } catch (e: Exception) {
                emitter.onError(e)
            }
        }

        return Observable.create(source)
    }

    override fun deleteWhere(query: Query): Observable<Any> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}