package fr.xgouchet.khronorg.ui.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.ui.Cutelry.knife
import fr.xgouchet.khronorg.ui.adapters.EditorAdapter
import fr.xgouchet.khronorg.ui.editor.EditorItem
import fr.xgouchet.khronorg.ui.presenters.EditorPresenter
import fr.xgouchet.khronorg.ui.views.EditorView
import kotlin.properties.Delegates.notNull

/**
 * @author Xavier F. Gouchet
 */
class EditorFragment<T>
    : Fragment(), EditorView {

    internal val list: RecyclerView by knife(android.R.id.list)
    internal val loading: ProgressBar by knife(R.id.loading)
    internal val fab: FloatingActionButton by knife(R.id.fab)
    internal val message: TextView by knife(R.id.message)

    var presenter: EditorPresenter<T> by notNull()
    val adapter = EditorAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): android.view.View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        return view
    }

    override fun onViewCreated(view: android.view.View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list.layoutManager = LinearLayoutManager(activity)
        list.adapter = adapter
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.editor, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) return false
        when (item.itemId) {
            R.id.editor_validate -> {
                presenter.applyEdition()
                return true
            }
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun setEmpty() {
        message.setText(R.string.empty_list)
        message.visibility = View.VISIBLE
        list.visibility = View.GONE
    }


    override fun setLoading(isLoading: Boolean) {
        loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        fab.visibility = View.GONE
    }

    override fun setError(throwable: Throwable) {
        message.text = getString(R.string.error_generic, throwable.message)
        message.visibility = View.VISIBLE
        list.visibility = View.GONE
    }

    override fun setContent(content: List<EditorItem>) {
        adapter.update(content)
        message.visibility = View.GONE
        list.visibility = View.VISIBLE
    }
}

