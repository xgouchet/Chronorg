package fr.xgouchet.chronorg.feature.project.list

import androidx.navigation.fragment.findNavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.BaseFragment


class ProjectListFragment
    : BaseFragment<ProjectsListViewModel>() {

    override val vmClass: Class<ProjectsListViewModel> = ProjectsListViewModel::class.java
    override val fabIcon: Int? = R.drawable.ic_fab_add

    override fun onFabClicked() {
        findNavController().navigate(R.id.projectEditorFragment)
    }

}
