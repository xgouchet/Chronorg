package fr.xgouchet.chronorg.feature.entity.timeline

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.navigation.fragment.findNavController
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.android.mvvm.BaseFragment
import fr.xgouchet.chronorg.data.flow.model.Entity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class EntityTimelineFragment
    : BaseFragment<EntityTimelineViewModel>() {

    override val vmClass: Class<EntityTimelineViewModel> = EntityTimelineViewModel::class.java

    // region Fragment

    override fun onResume() {
        super.onResume()
        activity?.title = getEntity().name
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
                vm.onEdit(findNavController())
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

    override fun configure(viewModel: EntityTimelineViewModel) {
        super.configure(viewModel)
        viewModel.entity = getEntity()
    }

    // endregion

    // region Internal

    private fun getEntity(): Entity {
        return requireArguments().getParcelable("entity")!!
    }

    // endregion
}