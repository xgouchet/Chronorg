package fr.xgouchet.khronorg.feature.projects

import android.net.Uri
import fr.xgouchet.khronorg.commons.ioproviders.IOProvider
import fr.xgouchet.khronorg.feature.projects.Project
import fr.xgouchet.khronorg.commons.queriers.BaseContentQuerier
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class ProjectQuerier(ioProvider: IOProvider<Project>) : BaseContentQuerier<Project>(ioProvider) {

    override val uri: Uri = KhronorgSchema.PROJECTS_URI

    override fun getId(item: Project): Int = item.id
}