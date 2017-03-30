package fr.xgouchet.khronorg.feature.projects

import android.database.Cursor
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.commons.queriers.ContentQuerier
import fr.xgouchet.khronorg.feature.projects.ProjectQuerier
import fr.xgouchet.khronorg.feature.projects.ProjectReader
import fr.xgouchet.khronorg.commons.readers.Reader
import fr.xgouchet.khronorg.feature.projects.ProjectWriter
import fr.xgouchet.khronorg.commons.writers.Writer

/**
 * @author Xavier F. Gouchet
 */
class ProjectProvider : IOProvider<Project> {

    override fun provideReader(cursor: Cursor): Reader<Project> = ProjectReader(cursor)

    override val writer: Writer<Project> = ProjectWriter()

    override val querier: ContentQuerier<Project> = ProjectQuerier(this)

}