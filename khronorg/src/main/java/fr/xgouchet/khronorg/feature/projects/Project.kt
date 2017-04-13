package fr.xgouchet.khronorg.feature.projects

import android.os.Parcel
import android.os.Parcelable
import fr.xgouchet.khronorg.commons.time.getLocalTimeZone
import org.joda.time.DateTime
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
data class Project(var id: Int = -1,
                   var name: String = "",
                   var min: ReadableInstant = DateTime("1985-10-26T01:35:00-08:00"),
                   var max: ReadableInstant = DateTime("1985-10-26T01:35:00-08:00"))
    : Parcelable {


    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            it.writeInt(id)
            it.writeString(name)
            it.writeString(min.toString())
            it.writeString(max.toString())
        }
    }

    constructor(input: Parcel) : this() {
        id = input.readInt()
        name = input.readString()
        min = DateTime(input.readString(), getLocalTimeZone())
        max = DateTime(input.readString(), getLocalTimeZone())
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