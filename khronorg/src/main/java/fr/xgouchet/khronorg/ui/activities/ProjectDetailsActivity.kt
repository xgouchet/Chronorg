package fr.xgouchet.khronorg.ui.activities

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.widget.Toast
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.Cutelry.knife
import fr.xgouchet.khronorg.ui.adapters.PagerFragmentAdapter
import fr.xgouchet.khronorg.ui.navigators.ProjectNavigator.Companion.EXTRA_PROJECT
import kotlin.properties.Delegates.notNull

/**
 * @author Xavier F. Gouchet
 */
class ProjectDetailsActivity : BaseActivity() {

    val viewPager: ViewPager by knife(R.id.pager)
    var pageAdapter: PagerFragmentAdapter by notNull()

    public override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pager)

        // Get project from intent
        val project = intent.getParcelableExtra<Project>(EXTRA_PROJECT)
        if (project == null) {
            Toast.makeText(this, R.string.error_project_details_empty, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        title = project.name

        pageAdapter = PagerFragmentAdapter(supportFragmentManager, this, project)
        viewPager.setAdapter(pageAdapter)

        kodein.instance<BaseRepository<Project>>().setCurrent(project)
    }


}