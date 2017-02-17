package fr.xgouchet.khronorg.ui.presenters

import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.repositories.ProjectRepository
import fr.xgouchet.khronorg.ui.navigators.ProjectNavigator
import io.reactivex.Observable

/**
 * @author Xavier F. Gouchet
 */
class ProjectListPresenter(val repository: ProjectRepository, navigator: ProjectNavigator) : BaseListPresenter<Project>(navigator) {


    override fun getItemsObservable(): Observable<Project> {
        return repository.get(Unit)
    }

}