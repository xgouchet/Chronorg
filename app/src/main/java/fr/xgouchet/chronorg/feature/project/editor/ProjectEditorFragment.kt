package fr.xgouchet.chronorg.feature.project.editor

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

class ProjectEditorFragment
    : BaseFragment<ProjectEditorViewModel>() {

    override val vmClass: Class<ProjectEditorViewModel> = ProjectEditorViewModel::class.java

    // region Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater.inflate(R.menu.editor, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val vm = viewModel ?: return false

        return when (item.itemId) {
            R.id.action_confirm -> {
                CoroutineScope(Dispatchers.Main).launch {

                    val result = async { vm.onSave() }
                    if (result.await()) {
                        findNavController().popBackStack()
                    }
                }
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // endregion

    // region BaseFragment

    override fun onFabClicked() {}

    // endregion
}
