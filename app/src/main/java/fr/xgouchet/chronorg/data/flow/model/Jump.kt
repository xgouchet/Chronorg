package fr.xgouchet.chronorg.data.flow.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.joda.time.Instant

@Parcelize
data class Jump(
    val id: Long,
    val entity: Entity,
    val name: String,
    val from: Instant,
    val to: Instant,
    val jumpOrder: Long,
    val portal: Portal?
) : Parcelable