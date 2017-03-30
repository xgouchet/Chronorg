package fr.xgouchet.khronorg.feature.projects

import android.app.Activity
import android.content.Intent
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.feature.projects.ProjectDetailsAktivity
import fr.xgouchet.khronorg.feature.projects.ProjectEditorAktivity
import fr.xgouchet.khronorg.ui.activities.TimelineAktivity
import fr.xgouchet.khronorg.ui.navigators.Navigator

/**
 * @author Xavier F. Gouchet
 */
class ProjectNavigator(val activity: Activity) : Navigator<Project> {

    companion object {
        val EXTRA_PROJECT = "project"
    }

    override fun goToItemDetails(item: Project) {
        val intent = Intent(activity, ProjectDetailsAktivity::class.java)
        intent.putExtra(EXTRA_PROJECT, item)
        activity.startActivity(intent)
    }

    override fun goToItemEdition(item: Project) {
        val intent = Intent(activity, ProjectEditorAktivity::class.java)
        intent.putExtra(EXTRA_PROJECT, item)
        activity.startActivity(intent)
    }

    override fun goToItemCreation() {
        val intent = Intent(activity, ProjectEditorAktivity::class.java)
        activity.startActivity(intent)
    }

    override fun goBack() {
        activity.finish()
    }

    fun goToTimeline(item: Project) {
        val intent = Intent(activity, TimelineAktivity::class.java)
        intent.putExtra(EXTRA_PROJECT, item)
        activity.startActivity(intent)
    }
}