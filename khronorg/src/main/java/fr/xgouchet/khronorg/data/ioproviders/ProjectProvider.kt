package fr.xgouchet.khronorg.data.ioproviders

import android.database.Cursor
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.data.queriers.ContentQuerier
import fr.xgouchet.khronorg.data.queriers.ProjectQuerier
import fr.xgouchet.khronorg.data.readers.ProjectReader
import fr.xgouchet.khronorg.data.readers.Reader
import fr.xgouchet.khronorg.data.writers.ProjectWriter
import fr.xgouchet.khronorg.data.writers.Writer

/**
 * @author Xavier F. Gouchet
 */
class ProjectProvider : IOProvider<Project> {

    override fun provideReader(cursor: Cursor): Reader<Project> = ProjectReader(cursor)

    override val writer: Writer<Project> = ProjectWriter()

    override val querier: ContentQuerier<Project> = ProjectQuerier(this)

}