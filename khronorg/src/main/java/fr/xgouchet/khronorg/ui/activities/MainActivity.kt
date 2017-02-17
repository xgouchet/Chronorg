package fr.xgouchet.khronorg.ui.activities

import android.os.Bundle
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.ioproviders.ProjectProvider
import fr.xgouchet.khronorg.data.repositories.ProjectRepository
import fr.xgouchet.khronorg.ui.fragments.ProjectListFragment
import fr.xgouchet.khronorg.ui.navigators.ProjectNavigator
import fr.xgouchet.khronorg.ui.presenters.ProjectListPresenter

/**
 * @author Xavier F. Gouchet
 */
class MainActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val repository = ProjectRepository(this, ProjectProvider())
            val fragment = ProjectListFragment()
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