package fr.xgouchet.khronorg.data.models

import android.os.Parcel
import android.os.Parcelable

/**
 * @author Xavier F. Gouchet
 */
data class Project(var id: Int = -1,
                   var name: String = "") : Parcelable {


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id)
        dest?.writeString(name)
    }

    constructor(input: Parcel) : this() {
        id = input.readInt()
        name = input.readString()
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Project> = object : Parcelable.Creator<Project> {
            override fun createFromParcel(source: Parcel): Project {
                return Project(source)
            }

            override fun newArray(size: Int): Array<Project?> {
                return arrayOfNulls(size)
            }
        }
    }
}