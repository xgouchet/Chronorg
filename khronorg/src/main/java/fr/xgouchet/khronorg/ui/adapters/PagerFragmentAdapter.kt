package fr.xgouchet.khronorg.ui.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.activities.BaseActivity
import fr.xgouchet.khronorg.ui.fragments.TravellerListFragment
import fr.xgouchet.khronorg.ui.navigators.TravellerNavigator
import fr.xgouchet.khronorg.ui.presenters.TravellerListPresenter
import java.lang.ref.WeakReference

/**
 * @author Xavier F. Gouchet
 */
class PagerFragmentAdapter(fm: FragmentManager, activity: BaseActivity, val project: Project) : FragmentStatePagerAdapter(fm) {

    companion object {

        val PAGE_TRAVELLERS = 0
        val PAGE_EVENTS = 1
        val PAGE_PORTALS = 2
        val PAGE_TIMELINES = 3
        val PAGE_COUNT = 4

    }

    val activityRef = WeakReference(activity)

    override fun getCount(): Int = 1 // PAGE_COUNT

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
//            PAGE_EVENTS -> {
//                val fragment = EventListFragment.createFragment(project.getId())
//                val presenter = activity.getActivityComponent().getEventListPresenter()
//                presenter.setProject(project)
//                presenter.setView(fragment)
//                return fragment
//            }
//            PAGE_PORTALS -> {
//                val fragment = PortalListFragment.createFragment(project.getId())
//                val presenter = activity.getActivityComponent().getPortalListPresenter()
//                presenter.setProject(project)
//                presenter.setView(fragment)
//                return fragment
//            }
//            PAGE_TIMELINES -> {
//                val fragment = TimelineListFragment.createFragment(project.getId())
//                val presenter = activity.getActivityComponent().getTimelineListPresenter()
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
