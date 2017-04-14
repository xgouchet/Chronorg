package fr.xgouchet.khronorg.feature.jumps

import android.os.Parcel
import android.os.Parcelable
import fr.xgouchet.khronorg.commons.time.getLocalTimeZone
import fr.xgouchet.khronorg.data.models.Direction
import org.joda.time.DateTime
import org.joda.time.Interval
import org.joda.time.ReadableInstant
import org.joda.time.ReadableInterval

/**
 * @author Xavier F. Gouchet
 */
data class Jump(var id: Int = -1,
                var travellerId: Int = -1,
                var order: Int = 0,
                var from: ReadableInstant = DateTime("1985-10-26T01:35:00-08:00"),
                var delay: ReadableInterval = Interval(DateTime("1955-11-05T06:15:00-08:00"), DateTime("1985-10-26T01:35:00-08:00")),
                var direction: Int = Direction.PAST)
    : Parcelable {

    constructor(id: Int = -1,
                travellerId: Int = -1,
                order: Int = 0,
                from: ReadableInstant = DateTime("1985-10-26T01:35:00-08:00"),
                dest: ReadableInstant = DateTime("1955-11-05T06:15:00-08:00"))
            : this(id, travellerId, order, from,
            if (from.isBefore(dest)) Interval(from, dest) else Interval(dest, from),
            if (from.isBefore(dest)) Direction.FUTURE else Direction.PAST)

    val destination: ReadableInstant
        get() {
            when (direction) {
                Direction.PAST -> return from.toInstant().minus(delay.toDuration())
                Direction.FUTURE -> return from.toInstant().plus(delay.toDuration())
                else -> throw IllegalArgumentException("Unknown jump direction : $direction")
            }
        }

    override fun describeContents(): Int {
        return 0
    }

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.let {
            it.writeInt(id)
            it.writeInt(travellerId)
            it.writeInt(order)
            it.writeString(from.toString())
            it.writeString(delay.toString())
            it.writeInt(direction)
        }
    }

    constructor(input: Parcel) : this() {
        id = input.readInt()
        travellerId = input.readInt()
        order = input.readInt()
        from = DateTime(input.readString(), getLocalTimeZone())
        delay = Interval(input.readString())
        direction = input.readInt()
    }

    companion object {
        @JvmField val CREATOR: Parcelable.Creator<Jump> = object : Parcelable.Creator<Jump> {
            override fun createFromParcel(source: Parcel): Jump {
                return Jump(source)
            }

            override fun newArray(size: Int): Array<Jump?> {
                return arrayOfNulls(size)
            }
        }

        val ID_BIRTH = -1;
        val ID_DEATH = -2;
    }
}