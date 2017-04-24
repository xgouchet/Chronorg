package fr.xgouchet.khronorg.ui.fragments

import android.app.Dialog
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.DialogFragment
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.ui.Cutelry.knife
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.presenters.ListPresenter
import fr.xgouchet.khronorg.ui.views.ListView
import io.reactivex.functions.Consumer
import kotlin.properties.Delegates.notNull

/**
 * @author Xavier F. Gouchet
 */
abstract class ListFragmentDialog<T>(val isFabVisible: Boolean)
    : DialogFragment(), ListView<T>, Consumer<T> {

    internal val list: RecyclerView by knife(android.R.id.list)
    internal val loading: ProgressBar by knife(R.id.loading)
    internal val fab: FloatingActionButton by knife(R.id.fab)
    internal val message: TextView by knife(R.id.message)

    var presenter: ListPresenter<T> by notNull()

    abstract val adapter: BaseAdapter<T>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)

        return view
    }


    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = adapter
    }

    override fun onStart() {
        super.onStart()
        dialog.window.setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
    }
    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun setEmpty() {
        message.setText(R.string.empty_list)
        message.visibility = View.VISIBLE
        list.visibility = View.GONE
    }


    override fun setLoading(isLoading: Boolean) {
        loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        fab.visibility = if (isLoading || !isFabVisible) View.GONE else View.VISIBLE
    }

    override fun setError(throwable: Throwable) {
        throwable.printStackTrace()
        message.text = getString(R.string.error_generic, throwable.message)
        message.visibility = View.VISIBLE
        list.visibility = View.GONE
    }

    override fun setContent(content: List<T>) {
        adapter.update(content)
        message.visibility = View.GONE
        list.visibility = View.VISIBLE
    }

    override fun accept(t: T) {
        presenter.itemSelected(t)
    }
}