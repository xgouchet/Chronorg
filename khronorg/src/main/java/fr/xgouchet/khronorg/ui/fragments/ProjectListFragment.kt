package fr.xgouchet.khronorg.ui.fragments

import android.os.Bundle
import android.view.View
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.ui.adapters.ProjectAdapter

/**
 * @author Xavier F. Gouchet
 */
class ProjectListFragment : ListFragment<Project>() {


    override val adapter: BaseAdapter<Project> = ProjectAdapter(this)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { e -> presenter.itemSelected(null) }
    }
}