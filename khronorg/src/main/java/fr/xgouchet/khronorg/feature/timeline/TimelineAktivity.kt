package fr.xgouchet.khronorg.feature.timeline

import android.os.Bundle
import android.widget.Toast
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.feature.jumps.JumpRepository
import fr.xgouchet.khronorg.feature.portals.Portal
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.feature.projects.ProjectNavigator.Companion.EXTRA_PROJECT
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.ui.activities.BaseAktivity
import kotlin.properties.Delegates

/**
 * @author Xavier F. Gouchet
 */
class TimelineAktivity : BaseAktivity() {


    var project: Project by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_single_fragment)

        // Get project from intent
        val p = intent.getParcelableExtra<Project>(EXTRA_PROJECT)
        if (p == null) {
            Toast.makeText(this, R.string.error_project_details_empty, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        project = p
        title = p.name

        val fragment = ShardListFragment()
        val travellerRepository = kodein.instance<BaseRepository<Traveller>>()
        val jumpRepository = kodein.instance<JumpRepository>()
        val eventRepository = kodein.instance<BaseRepository<Event>>()
        val portalRepository = kodein.instance<BaseRepository<Portal>>()
        val navigator = ShardNavigator(this)
        val presenter = ShardListPresenter(travellerRepository,
                jumpRepository,
                eventRepository,
                portalRepository,
                project,
                navigator)

        presenter.view = fragment
        fragment.presenter = presenter
        supportFragmentManager.beginTransaction()
                .add(R.id.root, fragment)
                .commit()
    }
}