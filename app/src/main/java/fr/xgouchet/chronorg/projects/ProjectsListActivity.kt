package fr.xgouchet.chronorg.projects

class ProjectsListActivity : TAProjectsActivity() {


    override fun instantiatePresenter(): TAProjectsContract.Presenter {
        return TAProjectsPresenter(ProjectsDataSource())
    }

    override fun instantiateFragment(): TAProjectsFragment {
        return ProjectsListFragment()
    }

    override fun onFabClicked() {

    }


}