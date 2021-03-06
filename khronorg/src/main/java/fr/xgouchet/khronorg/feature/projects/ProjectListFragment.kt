package fr.xgouchet.khronorg.feature.projects

import android.os.Bundle
import android.view.View
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.ui.adapters.BaseAdapter
import fr.xgouchet.khronorg.feature.projects.ProjectAdapter
import fr.xgouchet.khronorg.ui.fragments.ListFragment

/**
 * @author Xavier F. Gouchet
 */
class ProjectListFragment : ListFragment<Project>(true) {


    override val adapter: BaseAdapter<Project> = ProjectAdapter(this)

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        fab.setOnClickListener { e -> presenter.itemCreated() }
    }
}