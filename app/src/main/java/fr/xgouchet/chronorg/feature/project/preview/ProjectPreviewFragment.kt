package fr.xgouchet.chronorg.feature.project.preview

import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.BaseFragment
import fr.xgouchet.chronorg.data.flow.model.Project


class ProjectPreviewFragment
    : BaseFragment<ProjectPreviewViewModel>() {

    override val vmClass: Class<ProjectPreviewViewModel> = ProjectPreviewViewModel::class.java
    override val fabIcon: Int? = R.drawable.ic_fab_add

    // region Fragment

    override fun onResume() {
        super.onResume()
        activity?.title = getProject()?.name ?: "?"
    }

    // endregion

    // region BaseFragment

    override fun configure(viewModel: ProjectPreviewViewModel) {
        super.configure(viewModel)
        viewModel.project = getProject()
    }

    override fun onFabClicked() {
        findNavController().navigate(R.id.projectEditorFragment)
    }

    // endregion

    // region Internal

    private fun getProject(): Project? {
        return arguments?.getParcelable("project")
    }

    // endregion
}
