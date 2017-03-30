package fr.xgouchet.khronorg.feature.projects

import android.content.ContentValues
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.commons.writers.BaseWriter
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class ProjectWriter : BaseWriter<Project>() {
    override fun fillContentValues(cv: ContentValues, data: Project) {
        cv.put(KhronorgSchema.COL_NAME, data.name)
    }
}