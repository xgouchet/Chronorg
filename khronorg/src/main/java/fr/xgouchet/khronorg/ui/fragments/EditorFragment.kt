package fr.xgouchet.khronorg.ui.fragments

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
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
import fr.xgouchet.khronorg.ui.adapters.EditorAdapter
import fr.xgouchet.khronorg.ui.editor.EditorInterface
import fr.xgouchet.khronorg.ui.presenters.EditorPresenter
import fr.xgouchet.khronorg.ui.views.EditorView
import kotlin.properties.Delegates.notNull

/**
 * @author Xavier F. Gouchet
 */
abstract class EditorFragment<T>
    : Fragment(), EditorView<T> {

    internal val list: RecyclerView by knife(android.R.id.list)
    internal val loading: ProgressBar by knife(R.id.loading)
    internal val fab: FloatingActionButton by knife(R.id.fab)
    internal val message: TextView by knife(R.id.message)

    abstract val editorInterface: EditorInterface<T>
    var presenter: EditorPresenter<T> by notNull()
    val adapter = EditorAdapter()

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


    override fun setEmpty() {
        message.setText(R.string.empty_list)
        message.visibility = View.VISIBLE
        list.visibility = View.GONE
    }


    override fun setLoading(isLoading: Boolean) {
        loading.visibility = if (isLoading) View.VISIBLE else View.GONE
        fab.visibility = if (isLoading) View.GONE else View.VISIBLE
    }

    override fun setError(throwable: Throwable) {
        message.text = getString(R.string.error_generic, throwable.message)
        message.visibility = View.VISIBLE
        list.visibility = View.GONE
    }

    override fun setContent(content: T) {
        adapter.update(editorInterface.generateItems(content))
    }
}

