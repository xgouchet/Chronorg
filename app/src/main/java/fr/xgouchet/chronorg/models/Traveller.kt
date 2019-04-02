package fr.xgouchet.chronorg.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Traveller(var id: Int = -1,
                     var projectId: Int = -1,
                     var name: String = "")
    : Parcelable