package fr.xgouchet.chronorg.feature.portal.editor

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.BaseFragment
import fr.xgouchet.chronorg.android.mvvm.EditorFragment
import fr.xgouchet.chronorg.data.flow.model.Project
import fr.xgouchet.chronorg.feature.portal.list.PortalListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class PortalEditorFragment
    : EditorFragment<PortalEditorViewModel>() {

    override val vmClass: Class<PortalEditorViewModel> = PortalEditorViewModel::class.java

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

    override fun configure(viewModel: PortalEditorViewModel) {
        super.configure(viewModel)
        viewModel.project = getProject()
    }

    // endregion

    // region Internal

    private fun getProject(): Project? {
        return arguments?.getParcelable("project")
    }

    // endregion
}
