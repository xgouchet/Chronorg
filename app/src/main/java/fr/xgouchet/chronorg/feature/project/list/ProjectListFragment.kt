package fr.xgouchet.chronorg.feature.project.list

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.BaseFragment
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class ProjectListFragment
    : BaseFragment<ProjectsListViewModel>() {

    override val vmClass: Class<ProjectsListViewModel> = ProjectsListViewModel::class.java
    override val fabIcon: Int? = R.drawable.ic_fab_add

    // region Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.demo, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val vm = viewModel ?: return false

        return when (item.itemId) {
            R.id.action_demo -> {
                CoroutineScope(Dispatchers.Main).launch {
                    val result = async { vm.createDemoProject() }
                    if (result.await()) {
                        viewModel?.let { updateData(it) }
                    }
                }
                true
            }
            R.id.action_delete -> {
                TODO()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // endregion

    // region BaseFragment

    override fun onFabClicked() {
        findNavController().navigate(R.id.projectEditorFragment)
    }

    // endregion
}
