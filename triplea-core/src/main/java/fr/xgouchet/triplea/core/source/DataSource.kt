package fr.xgouchet.triplea.core.source

interface DataSource<T> {

    fun getData(onSuccess: (T) -> Unit,
                onError: (Throwable) -> Unit)

    fun cancel()
}