package fr.xgouchet.triplea.core.front.mvp

import android.support.v4.util.LruCache
import timber.log.Timber

object PresenterCache {

    const val MAX_SIZE: Int = 8

    val cache: LruCache<String, BaseContract.Presenter> = LruCache(MAX_SIZE)

    fun <P : BaseContract.Presenter> savePresenter(key: String, presenter: P) {
        cache.put(key, presenter)
    }

    fun <P : BaseContract.Presenter> getPresenter(key: String, factory: () -> P): P {
        var match: P? = null

        try {
            @Suppress("UNCHECKED_CAST")
            match = cache.get(key) as P
        } catch (e: ClassCastException) {
            Timber.w("Presenter found for key $key, but type doesn't match expected.")
        }

        if (match == null) {
            val presenter = factory()
            cache.put(key, presenter)
            return presenter
        } else {
            return match
        }

    }

    fun dropPresenter(key: String) {
        val removed = cache.remove(key)
        if (removed == null) {
            Timber.i("Removing presenter for key $key, but none was found. Was it pruned, or never saved ?")
        }
    }

    fun clear() {
        cache.evictAll()
    }

}