package fr.xgouchet.chronorg.feature.project.preview

import androidx.navigation.fragment.findNavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.BaseFragment


class ProjectPreviewFragment
    : BaseFragment<ProjectPreviewViewModel>() {

    override val vmClass: Class<ProjectPreviewViewModel> = ProjectPreviewViewModel::class.java
    override val fabIcon: Int? = R.drawable.ic_fab_add

    override fun onFabClicked() {
        findNavController().navigate(R.id.projectEditorFragment)
    }

}
