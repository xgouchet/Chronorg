package fr.xgouchet.khronorg.feature.projects

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.samples.BackToTheFutureSample
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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        super.onCreateOptionsMenu(menu)
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) return false
        when (item.itemId) {
            R.id.sample -> {
                BackToTheFutureSample(kodein, ProjectNavigator(this)).addSampleProject()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

}