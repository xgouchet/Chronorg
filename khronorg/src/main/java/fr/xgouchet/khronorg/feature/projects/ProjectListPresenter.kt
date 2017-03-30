package fr.xgouchet.khronorg.feature.projects

import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.projects.ProjectNavigator
import fr.xgouchet.khronorg.ui.presenters.BaseListPresenter
import io.reactivex.Observable

/**
 * @author Xavier F. Gouchet
 */
class ProjectListPresenter(val repository: BaseRepository<Project>, navigator: ProjectNavigator) : BaseListPresenter<Project>(navigator) {


    override fun getItemsObservable(): Observable<Project> {
        return repository.getAll()
    }

}