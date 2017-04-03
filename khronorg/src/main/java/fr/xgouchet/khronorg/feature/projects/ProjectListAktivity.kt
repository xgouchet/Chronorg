package fr.xgouchet.khronorg.feature.projects

import android.os.Bundle
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.projects.ProjectListFragment
import fr.xgouchet.khronorg.feature.projects.ProjectNavigator
import fr.xgouchet.khronorg.feature.projects.ProjectListPresenter
import fr.xgouchet.khronorg.ui.activities.BaseAktivity

/**
 * @author Xavier F. Gouchet
 */
class ProjectListAktivity : BaseAktivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_single_fragment)

        if (savedInstanceState == null) {
            val fragment = ProjectListFragment()
            val repository = kodein.instance<BaseRepository<Project>>()
            val navigator = ProjectNavigator(this)
            val presenter = ProjectListPresenter(repository, navigator)

            presenter.view = fragment
            fragment.presenter = presenter
            supportFragmentManager.beginTransaction()
                    .add(R.id.root, fragment)
                    .commit()
        }
    }
}