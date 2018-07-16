package fr.xgouchet.triplea.core.front.mvp

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter

abstract class BaseViewPagerAdapter(fm: FragmentManager)
    : FragmentStatePagerAdapter(fm) {

    private val fragments: MutableList<Fragment?> = mutableListOf()

    private var presenterToAttach: Triple<Int, BaseContract.Presenter, Boolean>? = null

    override fun getItem(position: Int): Fragment {
        val fragment = instantiateFragment(position)
        while (fragments.size <= position) {
            fragments.add(null)
        }
        fragments[position] = fragment
        presenterToAttach?.let {
            if (it.first == position && fragment is BaseContract.View) {
                it.second.onViewAttached(fragment, it.third)
            }
        }
        presenterToAttach = null
        return fragment
    }

    fun getFragment(position: Int): Fragment? {
        return fragments.getOrNull(position)
    }

    abstract fun instantiateFragment(position: Int): Fragment

    abstract fun instantiatePresenter(activity: Activity,
                                      position: Int): BaseContract.Presenter

    fun attachOnFragmentCreated(position: Int,
                                presenter: BaseContract.Presenter,
                                isRestored: Boolean) {
        presenterToAttach = Triple(position, presenter, isRestored)
    }

    fun cancelAttachOnFragmentCreated() {
        presenterToAttach = null
    }

}