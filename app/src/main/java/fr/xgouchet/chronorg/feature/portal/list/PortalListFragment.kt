package fr.xgouchet.chronorg.feature.portal.list

import android.os.Bundle
import androidx.navigation.fragment.findNavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.BaseFragment
import fr.xgouchet.chronorg.data.flow.model.Project

class PortalListFragment
    : BaseFragment<PortalListViewModel>() {

    override val vmClass: Class<PortalListViewModel> = PortalListViewModel::class.java
    override val fabIcon: Int? = R.drawable.ic_fab_add

    // region Fragment

    override fun onResume() {
        super.onResume()
        activity?.title = getProject()?.name ?: "?"
    }

    // endregion

    // region BaseFragment

    override fun configure(viewModel: PortalListViewModel) {
        super.configure(viewModel)
        viewModel.project = getProject()
    }

    override fun onFabClicked() {
        val bundle = Bundle(1)
        bundle.putParcelable("project", getProject())
        findNavController().navigate(R.id.portalEditorFragment, bundle)
    }

    // endregion

    // region Internal

    private fun getProject(): Project? {
        return arguments?.getParcelable("project")
    }

    // endregion
}
