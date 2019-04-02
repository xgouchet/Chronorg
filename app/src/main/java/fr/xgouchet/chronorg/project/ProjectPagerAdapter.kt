package fr.xgouchet.chronorg.project

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import fr.xgouchet.triplea.core.front.mvp.BaseContract

class ProjectPagerAdapter(fragmentManager: FragmentManager)
    : TAProjectPagerAdapter(fragmentManager) {

    override fun instantiateTravellersFragment(): Fragment {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun instantiateTravellersPresenter(activity: Activity): BaseContract.Presenter {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }


    override fun getPageTitle(position: Int): CharSequence? {
        return "PAGE $position"
    }


}