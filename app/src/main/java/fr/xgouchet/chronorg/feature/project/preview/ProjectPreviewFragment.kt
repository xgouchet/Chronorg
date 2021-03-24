package fr.xgouchet.chronorg.feature.project.preview

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.BaseFragment
import fr.xgouchet.chronorg.data.flow.model.Project
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProjectPreviewFragment
    : BaseFragment<ProjectPreviewViewModel>() {

    override val vmClass: Class<ProjectPreviewViewModel> = ProjectPreviewViewModel::class.java
    override val fabIcon: Int? = R.drawable.ic_fab_add

    // region Fragment

    override fun onResume() {
        super.onResume()
        activity?.title = getProject()?.name ?: "?"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.view, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val vm = viewModel ?: return false

        return when (item.itemId) {
            R.id.action_edit -> {
                TODO()
                // CoroutineScope(Dispatchers.Main).launch {
                //
                //     val result = async { vm.onSave() }
                //     if (result.await()) {
                //         findNavController().popBackStack()
                //     }
                // }
                true
            }
            R.id.action_delete -> {
               promptDeleteConfirmation(R.string.title_timelines) {
                   CoroutineScope(Dispatchers.Main).launch {

                       val result = async { vm.onDelete() }
                       if (result.await()) {
                           findNavController().popBackStack()
                       }
                   }
               }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
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
