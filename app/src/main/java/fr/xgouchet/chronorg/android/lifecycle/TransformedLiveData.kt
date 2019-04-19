package fr.xgouchet.chronorg.android.lifecycle

import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import fr.xgouchet.chronorg.ui.transformer.ViewModelListTransformer
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import kotlin.coroutines.CoroutineContext

class TransformedLiveData<I, O>(
        private val runContext: CoroutineContext = Dispatchers.Default,
        private val source: LiveData<I>,
        private val transform: (I) -> O
) : LiveData<O>() {

    private var job: Job? = null

    private val observer = Observer<I> { source ->
        job?.cancel()
        job = CoroutineScope(Dispatchers.Main).launch {
            val deferred = CoroutineScope(runContext).async { transform(source) }
            withContext(Dispatchers.Main) {
                value = deferred.await()
            }
        }
    }

    override fun onActive() {
        source.observeForever(observer)
    }

    override fun onInactive() {
        job?.cancel()
        source.removeObserver(observer)
    }
}

fun <I, O> LiveData<I>.map(
        runContext: CoroutineContext = Dispatchers.Default,
        transform: (I) -> O) = TransformedLiveData(runContext, this, transform)

fun <I, O> LiveData<List<I>>.mapList(
        runContext: CoroutineContext = Dispatchers.Default,
        transform: (I) -> O) = TransformedLiveData(runContext, this) { it.map(transform) }

fun <I> LiveData<I>.map(
        runContext: CoroutineContext = Dispatchers.Default,
        transformer: ViewModelListTransformer<I>) = TransformedLiveData(runContext, this) { transformer.transform(it) }

