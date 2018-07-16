package fr.xgouchet.chronorg.project

class ProjectActivity : TAProjectActivity() {

    override fun instantiateAdapter(): TAProjectPagerAdapter {
        return ProjectPagerAdapter(supportFragmentManager)
    }
}