package fr.xgouchet.khronorg.feature.portals

import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable
import fr.xgouchet.khronorg.data.models.Direction
import org.joda.time.DateTime
import org.joda.time.Duration
import org.joda.time.Interval
import org.joda.time.ReadableInterval

/**
 * @author Xavier F. Gouchet
 */
data class Portal(var id: Int = -1,
                  var projectId: Int = -1,
                  var name: String = "",
                  var delay: ReadableInterval = Interval(DateTime("1955-11-05T06:15:00-08:00"), DateTime("1985-10-26T01:35:00-08:00")),
                  var direction: Int = Direction.PAST,
                  var color: Int = Color.GRAY)
    : Parcelable {

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            it.writeInt(id)
            it.writeInt(projectId)
            it.writeString(name)
            it.writeString(delay.toString())
            it.writeInt(direction)
            it.writeInt(color)
        }
    }

    constructor(input: Parcel) : this() {
        id = input.readInt()
        projectId = input.readInt()
        name = input.readString()
        delay = Interval(input.readString())
        direction = input.readInt()
        color = input.readInt()
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Portal> = object : Parcelable.Creator<Portal> {
            override fun createFromParcel(source: Parcel): Portal {
                return Portal(source)
            }

            override fun newArray(size: Int): Array<Portal?> {
                return arrayOfNulls(size)
            }
        }
    }
}