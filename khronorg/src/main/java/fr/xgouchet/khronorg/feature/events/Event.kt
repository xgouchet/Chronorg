package fr.xgouchet.khronorg.feature.events

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import fr.xgouchet.khronorg.commons.time.getLocalTimeZone
import org.joda.time.DateTime
import org.joda.time.ReadableInstant

/**
 * @author Xavier F. Gouchet
 */
data class Event(var id: Int = -1,
                 var projectId: Int = -1,
                 var name: String = "",
                 var instant: ReadableInstant = DateTime("1984-04-20T18:00:00Z"),
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
            it.writeString(instant.toString())
            it.writeInt(color)
        }
    }

    constructor(input: Parcel) : this() {
        id = input.readInt()
        projectId = input.readInt()
        name = input.readString()
        instant = DateTime(input.readString(), getLocalTimeZone())
        color = input.readInt()
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Event> = object : Parcelable.Creator<Event> {
            override fun createFromParcel(source: Parcel): Event {
                return Event(source)
            }

            override fun newArray(size: Int): Array<Event?> {
                return arrayOfNulls(size)
            }
        }
    }
}