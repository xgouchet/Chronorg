package fr.xgouchet.chronorg.data.models;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Interval;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * @author Xavier Gouchet
 */
public class Portal implements Parcelable {

    @Retention(RetentionPolicy.CLASS)
    @IntDef(value = {Direction.FUTURE, Direction.PAST, Direction.BOTH}, flag = true)
    public @interface Direction {
        public static final int FUTURE = 1;
        public static final int PAST = 1 << 1;
        public static final int BOTH = FUTURE | PAST;
    }

    private int id = -1;
    private int projectId = -1;
    @NonNull private String name = "";
    @NonNull private Interval delay = new Interval(
            new DateTime(1955, 11, 12, 6, 0, 0, 0, DateTimeZone.UTC),
            new DateTime(1985, 10, 27, 2, 42, 0, 0, DateTimeZone.UTC));
    @Direction private int direction = Direction.BOTH;
    private boolean timeline;
    private int color = Color.RED;

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull public String getName() {
        return name;
    }

    public void setDelay(@NonNull String delay) {
        this.delay = new Interval(delay);
    }

    public void setDelay(@NonNull Interval delay) {
        this.delay = delay;
    }

    @NonNull public Interval getDelay() {
        return delay;
    }

    public void setDirection(@Direction int direction) {
        this.direction = direction;
    }

    @Direction public int getDirection() {
        return direction;
    }

    public void setTimeline(boolean timeline) {
        this.timeline = timeline;
    }

    public boolean isTimeline() {
        return timeline;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.projectId);
        dest.writeString(this.name);
        dest.writeString(this.delay.toString());
        dest.writeInt(this.direction);
        dest.writeByte(this.timeline ? (byte) 1 : (byte) 0);
        dest.writeInt(this.color);
    }

    public Portal() {
    }

    protected Portal(Parcel in) {
        this.id = in.readInt();
        this.projectId = in.readInt();
        this.name = in.readString();
        this.delay = new Interval(in.readString());
        //noinspection WrongConstant
        this.direction = in.readInt();
        this.timeline = in.readByte() != 0;
        this.color = in.readInt();
    }

    public static final Parcelable.Creator<Portal> CREATOR = new Parcelable.Creator<Portal>() {
        @Override public Portal createFromParcel(Parcel source) {
            return new Portal(source);
        }

        @Override public Portal[] newArray(int size) {
            return new Portal[size];
        }
    };
}
