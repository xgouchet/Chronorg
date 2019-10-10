package fr.xgouchet.chronorg.data.flow.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
class Project(
        val id: Long,
        val name: String,
        val description: String
) : Parcelable
