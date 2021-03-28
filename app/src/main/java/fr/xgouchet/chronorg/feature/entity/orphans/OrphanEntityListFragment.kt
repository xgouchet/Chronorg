package fr.xgouchet.chronorg.feature.entity.orphans

import fr.xgouchet.chronorg.android.mvvm.BaseFragment

class OrphanEntityListFragment
    : BaseFragment<OrphanEntityListViewModel>() {

    override val vmClass: Class<OrphanEntityListViewModel> = OrphanEntityListViewModel::class.java

    // region Fragment

    override fun onResume() {
        super.onResume()
        activity?.title = "Orphan Travelling Entities"
    }

    // endregion
}
