package fr.xgouchet.chronorg.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

/**
 * @author Xavier Gouchet
 */
public class Event implements Parcelable {

    private int id;
    private int projectId;
    @NonNull private String name = "";
    @NonNull private ReadableInstant instant = new DateTime("1970-01-01T00:00:00Z");
    @ColorInt private int color;

    public Event() {
    }


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

    public void setInstant(@NonNull ReadableInstant instant) {
        this.instant = instant;
    }

    public void setInstant(@NonNull String instant) {
        this.instant = new DateTime(instant);
    }

    @NonNull public ReadableInstant getInstant() {
        return instant;
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
    }

    @ColorInt public int getColor() {
        return color;
    }


    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.projectId);
        dest.writeString(this.name);
        dest.writeString(this.instant.toString());
        dest.writeInt(this.color);
    }

    protected Event(Parcel in) {
        this.id = in.readInt();
        this.projectId = in.readInt();
        this.name = in.readString();
        this.instant = new DateTime(in.readString());
        this.color = in.readInt();
    }

    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>() {
        @Override public Event createFromParcel(Parcel source) {
            return new Event(source);
        }

        @Override public Event[] newArray(int size) {
            return new Event[size];
        }
    };
}
