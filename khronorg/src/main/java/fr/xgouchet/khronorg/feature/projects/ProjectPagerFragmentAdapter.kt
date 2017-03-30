package fr.xgouchet.khronorg.feature.projects

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.feature.events.EventListFragment
import fr.xgouchet.khronorg.feature.events.EventListPresenter
import fr.xgouchet.khronorg.feature.events.EventNavigator
import fr.xgouchet.khronorg.feature.travellers.*
import fr.xgouchet.khronorg.ui.activities.BaseAktivity
import java.lang.ref.WeakReference

/**
 * @author Xavier F. Gouchet
 */
class ProjectPagerFragmentAdapter(fm: FragmentManager, aktivity: BaseAktivity, val project: Project) : FragmentStatePagerAdapter(fm) {

    companion object {

        val PAGE_TRAVELLERS = 0
        val PAGE_EVENTS = 1
        val PAGE_PORTALS = 2
        val PAGE_TIMELINES = 3
        val PAGE_COUNT = 4

    }

    val activityRef = WeakReference(aktivity)

    override fun getCount(): Int = 2 // PAGE_COUNT

    override fun getItem(position: Int): Fragment {
        val activity = activityRef.get() ?: throw IllegalStateException()
        val project = activity.kodein.instance<BaseRepository<Project>>().getCurrent()

        when (position) {
            PAGE_TRAVELLERS -> {
                val repository = activity.kodein.instance<BaseRepository<Traveller>>()
                val fragment = TravellerListFragment()
                val navigator = TravellerNavigator(activity)
                val presenter = TravellerListPresenter(repository, project, navigator)
                fragment.presenter = presenter
                presenter.view = fragment
                return fragment
            }
            PAGE_EVENTS -> {
                val repository = activity.kodein.instance<BaseRepository<Event>>()
                val fragment = EventListFragment()
                val navigator = EventNavigator(activity)
                val presenter = EventListPresenter(repository, project, navigator)
                fragment.presenter = presenter
                presenter.view = fragment
                return fragment
            }
//            PAGE_PORTALS -> {
//                val fragment = PortalListFragment.createFragment(project.getId())
//                val presenter = aktivity.getActivityComponent().getPortalListPresenter()
//                presenter.setProject(project)
//                presenter.setView(fragment)
//                return fragment
//            }
//            PAGE_TIMELINES -> {
//                val fragment = TimelineListFragment.createFragment(project.getId())
//                val presenter = aktivity.getActivityComponent().getTimelineListPresenter()
//                presenter.setProject(project)
//                presenter.setView(fragment)
//                return fragment
//            }

            else -> throw IllegalArgumentException()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val context = activityRef.get() ?: return null
        when (position) {
            PAGE_TRAVELLERS -> return context.getString(R.string.title_travellers)
            PAGE_EVENTS -> return context.getString(R.string.title_events)
            PAGE_PORTALS -> return context.getString(R.string.title_portals)
            PAGE_TIMELINES -> return context.getString(R.string.title_timelines)
        }


        return super.getPageTitle(position)
    }
}
