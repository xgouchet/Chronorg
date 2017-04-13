package fr.xgouchet.khronorg.feature.projects

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.events.Event
import fr.xgouchet.khronorg.feature.events.EventListFragment
import fr.xgouchet.khronorg.feature.events.EventListPresenter
import fr.xgouchet.khronorg.feature.events.EventNavigator
import fr.xgouchet.khronorg.feature.portals.PortalListFragment
import fr.xgouchet.khronorg.feature.jumps.PortalListPresenter
import fr.xgouchet.khronorg.feature.jumps.PortalNavigator
import fr.xgouchet.khronorg.feature.portals.Portal
import fr.xgouchet.khronorg.feature.travellers.Traveller
import fr.xgouchet.khronorg.feature.travellers.TravellerListFragment
import fr.xgouchet.khronorg.feature.travellers.TravellerListPresenter
import fr.xgouchet.khronorg.feature.travellers.TravellerNavigator
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
        val PAGE_COUNT = 3

    }

    val activityRef = WeakReference(aktivity)

    override fun getCount(): Int = PAGE_COUNT

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
            PAGE_PORTALS -> {
                val repository = activity.kodein.instance<BaseRepository<Portal>>()
                val fragment = PortalListFragment()
                val navigator = PortalNavigator(activity)
                val presenter = PortalListPresenter(repository, project, navigator)
                fragment.presenter = presenter
                presenter.view = fragment
                return fragment
            }

            else -> throw IllegalArgumentException()
        }
    }

    override fun getPageTitle(position: Int): CharSequence? {
        val context = activityRef.get() ?: return null
        when (position) {
            PAGE_TRAVELLERS -> return context.getString(R.string.title_travellers)
            PAGE_EVENTS -> return context.getString(R.string.title_events)
            PAGE_PORTALS -> return context.getString(R.string.title_portals)
        }


        return super.getPageTitle(position)
    }
}
