package fr.xgouchet.chronorg.feature.portal.orphans

import fr.xgouchet.chronorg.android.mvvm.BaseFragment

class OrphanPortalListFragment
    : BaseFragment<OrphanPortalListViewModel>() {

    override val vmClass: Class<OrphanPortalListViewModel> = OrphanPortalListViewModel::class.java

    // region Fragment

    override fun onResume() {
        super.onResume()
        activity?.title = "Orphan Travelling Portals"
    }

    // endregion
}
