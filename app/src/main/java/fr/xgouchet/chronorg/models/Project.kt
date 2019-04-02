package fr.xgouchet.chronorg.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Project(val id: Int,
                   val name: String)
    : Parcelable
// TODO add min/max dates