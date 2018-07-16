package fr.xgouchet.chronorg.project

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import fr.xgouchet.chronorg.projects.ProjectsDataSource
import fr.xgouchet.chronorg.projects.ProjectsListFragment
import fr.xgouchet.chronorg.projects.TAProjectsPresenter
import fr.xgouchet.triplea.core.front.mvp.BaseContract

class ProjectPagerAdapter(fragmentManager: FragmentManager)
    : TAProjectPagerAdapter(fragmentManager) {

    override fun instantiateProjectsFragment(): Fragment {
        return ProjectsListFragment()
    }

    override fun instantiateProjectsPresenter(activity: Activity): BaseContract.Presenter {
        return TAProjectsPresenter(ProjectsDataSource())
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return "PAGE $position"
    }


}