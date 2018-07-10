package fr.xgouchet.chronorg.projects

import fr.xgouchet.chronorg.models.Project
import fr.xgouchet.triplea.core.source.DataSource
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class ProjectsDataSource : DataSource<List<Project>> {

    private var disposable: Disposable? = null

    override fun getData(onSuccess: (List<Project>) -> Unit, onError: (Throwable) -> Unit) {
        disposable?.dispose()
        disposable = Observable.just("foo", "bar", "baz", "eggs", "bacon")
                .delay(3, TimeUnit.SECONDS)
                .map { Project(it) }
                .toList()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(onSuccess, onError)
    }

    override fun cancel() {
        disposable?.dispose()
        disposable = null
    }
}