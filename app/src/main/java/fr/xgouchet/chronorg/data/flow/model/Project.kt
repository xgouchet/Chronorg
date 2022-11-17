package fr.xgouchet.chronorg.data.flow.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Project(
    val id: Long,
    val name: String,
    val description: String,
    @Transient val entityCount: Int,
    @Transient val portalCount: Int,
    @Transient val eventCount: Int
) : Parcelable {
    companion object {
        val EMPTY = Project(0, "", "", 0, 0, 0)
    }
}
