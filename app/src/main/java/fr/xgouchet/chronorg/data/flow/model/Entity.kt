package fr.xgouchet.chronorg.data.flow.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.joda.time.Instant

@Parcelize
data class Entity(
    val id: Long,
    val project: Project,
    val name: String,
    val notes: String,
    val birth: Instant,
    val death: Instant
) : Parcelable {
    companion object {
        val EMPTY = Entity(0, Project.EMPTY, "", "", Instant(), Instant())
    }
}