package fr.xgouchet.chronorg.feature.jump.editor

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.EditorFragment
import fr.xgouchet.chronorg.data.flow.model.Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.joda.time.Instant

class JumpEditorFragment
    : EditorFragment<JumpEditorViewModel>() {

    override val vmClass: Class<JumpEditorViewModel> = JumpEditorViewModel::class.java

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

    override fun configure(viewModel: JumpEditorViewModel) {
        super.configure(viewModel)
        viewModel.entity = getEntity()
        viewModel.jumpOrder = getJumpOrder()
        viewModel.fromAfter = getFromAfter()
        viewModel.toBefore = getToBefore()
    }

    // endregion

    // region Internal

    private fun getEntity(): Entity {
        return arguments?.getParcelable("entity")!!
    }

    private fun getJumpOrder(): Long {
        return arguments?.getLong("order") ?: 0L
    }

    private fun getFromAfter(): Instant {
        return Instant(arguments?.getString("from_after")!!)
    }

    private fun getToBefore(): Instant {
        return Instant(arguments?.getString("to_before")!!)
    }

    // endregion
}
