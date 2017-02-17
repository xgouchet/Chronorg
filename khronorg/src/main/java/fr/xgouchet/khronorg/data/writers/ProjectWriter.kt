package fr.xgouchet.khronorg.data.writers

import android.content.ContentValues
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class ProjectWriter : BaseWriter<Project>() {
    override fun fillContentValues(cv: ContentValues, data: Project) {
        cv.put(KhronorgSchema.COL_NAME, data.name)
    }
}