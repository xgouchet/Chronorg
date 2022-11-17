package fr.xgouchet.chronorg.android.mvvm

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.google.android.material.floatingactionbutton.FloatingActionButton
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.di.ViewModelFactory
import fr.xgouchet.chronorg.ui.adapter.ItemAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.direct

abstract class BaseFragment<VM>
    : Fragment(),
    KodeinAware
    where VM : BaseViewModel,
          VM : ViewModel {

    abstract val vmClass: Class<VM>
    protected var viewModel: VM? = null
        private set

    private lateinit var contentView: View
    private lateinit var refreshLayout: SwipeRefreshLayout
    private lateinit var recyclerView: RecyclerView
    private lateinit var fab: FloatingActionButton

    private val adapter = ItemAdapter {
        CoroutineScope(Dispatchers.Main).launch {
            viewModel?.onViewEvent(it, findNavController())
        }
    }

    @DrawableRes
    open val fabIcon: Int? = null
    open val userCanRefresh: Boolean = false

    // region Fragment

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        contentView = inflater.inflate(R.layout.fragment_base, container, false)
        bindViews()
        setupViews()
        return contentView
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val viewModelFactory = ViewModelFactory(direct)
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(vmClass)
        viewModel?.let {
            configure(it)
            updateData(it)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        viewModel?.let {
            it.onActivityResult(requestCode, resultCode, data)
            updateData(it)
        }
    }

    override fun onResume() {
        super.onResume()
        activity?.setTitle(R.string.app_name)
        viewModel?.let {
            it.setLinkedFragment(this)
            updateData(it)
        }
    }

    override fun onPause() {
        super.onPause()
        viewModel?.setLinkedFragment(null)
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel = null
    }

    // endregion

    // region KodeinAware

    override val kodein: Kodein by lazy { (context?.applicationContext as KodeinAware).kodein }

    // endregion

    // region UX

    fun promptDeleteConfirmation(
        @StringRes title: Int,
        @StringRes message: Int = R.string.confirm_delete,
        delete: () -> Unit = {}
    ) {
        val dialog = AlertDialog.Builder(requireContext())
            .setTitle(title)
            .setMessage(message)
            .setNeutralButton(android.R.string.cancel) { _, _ ->  }
            .setNegativeButton(R.string.action_delete) { _, _ -> delete() }
            .create()

        dialog.show()
    }

    // endregion

    // region Internal

    @SuppressLint("RestrictedApi")
    private fun setupViews() {
        val iconRes = fabIcon
        if (iconRes == null) {
            fab.visibility = View.GONE
        } else {
            fab.setImageResource(iconRes)
            fab.visibility = View.VISIBLE
            fab.setOnClickListener { onFabClicked() }
        }

        recyclerView.layoutManager = LinearLayoutManager(recyclerView.context)
        recyclerView.adapter = adapter

        refreshLayout.isEnabled = userCanRefresh
    }

    private fun bindViews() {
        refreshLayout = contentView.findViewById(R.id.swipe_refresh)
        fab = contentView.findViewById(R.id.fa_button)
        recyclerView = contentView.findViewById(R.id.recycler_view)
    }

    protected fun updateData(vm: VM) {
        CoroutineScope(Dispatchers.Main).launch {
            val data = async { vm.getData() }
            adapter.updateData(data.await())
            activity?.invalidateOptionsMenu()
        }
    }

    // endregion

    open fun configure(viewModel: VM) {}

    open fun onFabClicked() {}
}
