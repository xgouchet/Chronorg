package fr.xgouchet.khronorg.ui.activities

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.Cutelry.knife
import fr.xgouchet.khronorg.ui.adapters.PagerFragmentAdapter
import fr.xgouchet.khronorg.ui.navigators.ProjectNavigator
import fr.xgouchet.khronorg.ui.navigators.ProjectNavigator.Companion.EXTRA_PROJECT
import kotlin.properties.Delegates.notNull

/**
 * @author Xavier F. Gouchet
 */
class ProjectDetailsAktivity : BaseAktivity() {

    val viewPager: ViewPager by knife(R.id.pager)
    var pageAdapter: PagerFragmentAdapter by notNull()

    var project: Project by notNull()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)

        // Get project from intent
        val p = intent.getParcelableExtra<Project>(EXTRA_PROJECT)
        if (p == null) {
            Toast.makeText(this, R.string.error_project_details_empty, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        project = p
        title = p.name

        pageAdapter = PagerFragmentAdapter(supportFragmentManager, this, p)
        viewPager.setAdapter(pageAdapter)

        kodein.instance<BaseRepository<Project>>().setCurrent(p)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.project_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var result = true

        when (item.itemId) {
            R.id.timeline -> {
            }
            R.id.edit -> {
                ProjectNavigator(this).goToItemEdition(project)
            }
            R.id.delete -> {
                // TODO
            }
            else -> result = super.onOptionsItemSelected(item)
        }
        return result
    }

}