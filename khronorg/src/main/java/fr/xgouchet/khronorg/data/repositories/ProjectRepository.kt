package fr.xgouchet.khronorg.data.repositories

import android.content.Context
import fr.xgouchet.khronorg.data.ioproviders.IOProvider
import fr.xgouchet.khronorg.data.models.Project
import io.reactivex.Observable
import io.reactivex.ObservableOnSubscribe
import io.reactivex.functions.Consumer

/**
 * @author Xavier F. Gouchet
 */
class ProjectRepository(val context: Context, val provider: IOProvider<Project>)
    : Repository<Unit?, Project> {

    override fun get(input: Unit?): Observable<Project> {
        val source = ObservableOnSubscribe<Project> {
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

    override fun save(item: Project): Observable<Any> {
        val project = item
        val source = ObservableOnSubscribe<Any> {
            emitter ->
            try {
                val contentResolver = context.contentResolver
                if (provider.querier.save(contentResolver, project)) {
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

}