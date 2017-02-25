package fr.xgouchet.khronorg.data.queriers

import android.net.Uri
import fr.xgouchet.khronorg.data.ioproviders.IOProvider
import fr.xgouchet.khronorg.data.models.Project
import fr.xgouchet.khronorg.provider.KhronorgSchema

/**
 * @author Xavier F. Gouchet
 */
class ProjectQuerier(ioProvider: IOProvider<Project>) : BaseContentQuerier<Project>(ioProvider) {

    override val uri: Uri = KhronorgSchema.PROJECTS_URI

    override fun getId(item: Project): Int = item.id
}