package fr.xgouchet.chronorg.data.flow.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
class Project(
        val id: Long,
        val name: String,
        val description: String
) : Parcelable
