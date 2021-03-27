package fr.xgouchet.chronorg.data.flow.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import org.joda.time.Interval

@Parcelize
data class Portal(
    val id: Long,
    val projectId: Long,
    val name: String,
    val notes: String,
    val delay: Interval,
    val direction: Direction    
) : Parcelable