package fr.xgouchet.chronorg.feature.event.orphans

import fr.xgouchet.chronorg.android.mvvm.BaseFragment

class OrphanEventListFragment
    : BaseFragment<OrphanEventListViewModel>() {

    override val vmClass: Class<OrphanEventListViewModel> = OrphanEventListViewModel::class.java

    // region Fragment

    override fun onResume() {
        super.onResume()
        activity?.title = "Orphan Travelling Events"
    }

    // endregion
}
