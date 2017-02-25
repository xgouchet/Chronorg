package fr.xgouchet.khronorg.ui.presenters

import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.navigators.ProjectNavigator
import io.reactivex.Observable

/**
 * @author Xavier F. Gouchet
 */
class ProjectListPresenter(val repository: BaseRepository<Project>, navigator: ProjectNavigator) : BaseListPresenter<Project>(navigator) {


    override fun getItemsObservable(): Observable<Project> {
        return repository.getAll()
    }

}