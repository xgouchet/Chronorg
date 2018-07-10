package fr.xgouchet.chronorg.projects

class ProjectsListActivity : TAProjectActivity() {


    override fun instantiatePresenter(): TAProjectContract.Presenter {
        return TAProjectPresenter(ProjectsDataSource())
    }

    override fun instantiateFragment(): TAProjectFragment {
        return ProjectsListFragment()
    }


}