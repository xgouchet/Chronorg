package fr.xgouchet.chronorg.projects

import fr.xgouchet.chronorg.models.Project
import io.reactivex.Observable
import java.util.concurrent.TimeUnit

class ProjectsDataSource : TAProjectsDataSource() {

    override fun observeData(): Observable<List<Project>> {
        return Observable.just("foo", "bar", "baz", "eggs", "bacon")
                .map { Project(it) }
                .toList()
                .delay(1, TimeUnit.SECONDS)
                .flatMapObservable { Observable.just(it) }
    }
}