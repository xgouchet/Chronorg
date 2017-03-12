package fr.xgouchet.khronorg.ui.activities

import android.os.Bundle
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.fragments.ProjectListFragment
import fr.xgouchet.khronorg.ui.navigators.ProjectNavigator
import fr.xgouchet.khronorg.ui.presenters.ProjectListPresenter

/**
 * @author Xavier F. Gouchet
 */
class MainAktivity : BaseAktivity() {

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