package fr.xgouchet.chronorg.data.flow.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.joda.time.Instant

@Parcelize
data class Event (
    val id: Long,
    val projectId : Long,
    val name: String,
    val notes: String,
    val date : Instant
) : Parcelable