package fr.xgouchet.khronorg.feature.travellers

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import fr.xgouchet.khronorg.commons.time.getLocalTimeZone
import org.joda.time.DateTime
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
data class Traveller(var id: Int = -1,
                     var projectId: Int = -1,
                     var name: String = "",
                     var birth: ReadableInstant = DateTime("1970-01-01T00:00:00Z"),
                     var death: ReadableInstant = DateTime("2038-01-19T03:14:17Z"),
                     var color: Int = Color.rgb(0xF6, 0x40, 0x2C))
    : Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            it.writeInt(id)
            it.writeInt(projectId)
            it.writeString(name)
            it.writeString(birth.toString())
            it.writeString(death.toString())
            it.writeInt(color)
        }
    }

    constructor(input: Parcel) : this() {
        id = input.readInt()
        projectId = input.readInt()
        name = input.readString()
        birth = DateTime(input.readString(), getLocalTimeZone())
        death = DateTime(input.readString(), getLocalTimeZone())
        color = input.readInt()
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Traveller> = object : Parcelable.Creator<Traveller> {
            override fun createFromParcel(source: Parcel): Traveller {
                return Traveller(source)
            }

            override fun newArray(size: Int): Array<Traveller?> {
                return arrayOfNulls(size)
            }
        }
    }
}