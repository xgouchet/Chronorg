package fr.xgouchet.khronorg.feature.editor

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.*
import android.widget.ProgressBar
import android.widget.TextView
import com.android.colorpicker.ColorPickerDialog
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.commons.formatters.DefaultInstantFormatter
import fr.xgouchet.khronorg.commons.formatters.DefaultIntervalFormatter
import fr.xgouchet.khronorg.commons.repositories.BaseRepository
import fr.xgouchet.khronorg.feature.editor.items.EditorColorItem
import fr.xgouchet.khronorg.feature.editor.items.EditorInstantItem
import fr.xgouchet.khronorg.feature.editor.items.EditorItem
import fr.xgouchet.khronorg.feature.editor.items.EditorPortalItem
import fr.xgouchet.khronorg.feature.jumps.PortalListPresenter
import fr.xgouchet.khronorg.feature.portals.Portal
import fr.xgouchet.khronorg.ui.Cutelry.knife
import fr.xgouchet.khronorg.ui.activities.BaseAktivity
import fr.xgouchet.khronorg.ui.dialog.InstantPickerDialog
import fr.xgouchet.khronorg.ui.dialog.PortalPickerDialog
import fr.xgouchet.khronorg.ui.navigators.SimpleNavigator
import fr.xgouchet.khronorg.ui.presenters.InstantPickerPresenter
import io.reactivex.functions.Consumer
import kotlin.properties.Delegates.notNull

/**
 * @author Xavier F. Gouchet
 */
class EditorFragment<T>
    : Fragment(), EditorView, EditorAdapterListener {


    internal val list: RecyclerView by knife(android.R.id.list)
    internal val loading: ProgressBar by knife(R.id.loading)
    internal val fab: FloatingActionButton by knife(R.id.fab)
    internal val message: TextView by knife(R.id.message)

    var presenter: EditorPresenter<T> by notNull()
    val adapter = EditorAdapter(DefaultInstantFormatter, DefaultIntervalFormatter, this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_list, container, false)
        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
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

        menu?.findItem(R.id.editor_delete)?.isVisible = presenter.isDeletable
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item == null) return false
        when (item.itemId) {
            R.id.editor_validate -> {
                presenter.applyEdition()
                return true
            }

            R.id.editor_delete -> {
                presenter.delete()
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

    override fun pickColor(colorItem: EditorColorItem?) {

        if (colorItem == null) return

        val pickableColors = resources.getIntArray(R.array.pickable_colors)
        val title = R.string.color_picker_default_title
        val selectedColor = 0 // TODO
        val columns = 4
        val dialog = ColorPickerDialog.newInstance(title, pickableColors, selectedColor, columns, ColorPickerDialog.SIZE_SMALL)

        dialog.setOnColorSelectedListener({ c ->
            colorItem.color = c
            adapter.notifyDataSetChanged()
        })

        dialog.show(activity.fragmentManager, "foo")
    }

    override fun pickInstant(instantItem: EditorInstantItem?) {
        if (instantItem == null) return

        val dialog = InstantPickerDialog(activity, instantItem.instant)
        val presenter = InstantPickerPresenter(Consumer { i ->
            instantItem.instant = i
            adapter.notifyDataSetChanged()
        })

        dialog.presenter = presenter
        presenter.view = dialog

        presenter.onReady()
    }

    override fun pickPortal(portalItem: EditorPortalItem?) {
        if (portalItem == null) return

        val dialog = PortalPickerDialog()
        val repository = (activity as BaseAktivity).kodein.instance<BaseRepository<Portal>>()
        val navigator = SimpleNavigator<Portal>(details = { p ->
            portalItem.portal = p
            dialog.dismiss()
            adapter.notifyDataSetChanged()
        })

        val presenter = PortalListPresenter(repository, portalItem.project, navigator)

        dialog.presenter = presenter
        presenter.view = dialog

        dialog.show(activity.supportFragmentManager, "Go!")

    }
}

