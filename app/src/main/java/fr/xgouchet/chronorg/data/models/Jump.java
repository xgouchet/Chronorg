package fr.xgouchet.chronorg.data.models;

import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import org.joda.time.DateTime;
import org.joda.time.ReadableInstant;

/**
 * @author Xavier Gouchet
 */
public class Jump implements Parcelable {

    private int id;
    private int entityId;
    private int order;
    @NonNull private String name = "";
    @NonNull private ReadableInstant from = new DateTime("1985-10-26T01:35:00-08:00");
    @NonNull private ReadableInstant to = new DateTime("1955-11-05T06:15:00-08:00");

    public Jump() {
    }

    public Jump(@NonNull ReadableInstant from, @NonNull ReadableInstant to) {
        this.from = from;
        this.to = to;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }

    public int getEntityId() {
        return entityId;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    @NonNull public String getName() {
        return name;
    }

    public void setFrom(@NonNull ReadableInstant from) {
        this.from = from;
    }

    public void setFrom(@NonNull String from) {
        this.from = new DateTime(from);
    }

    @NonNull public ReadableInstant getFrom() {
        return from;
    }

    public void setTo(@NonNull ReadableInstant to) {
        this.to = to;
    }

    public void setTo(@NonNull String to) {
        this.to = new DateTime(to);
    }

    @NonNull public ReadableInstant getTo() {
        return to;
    }

    public void setOrder(int order) {
        this.order = order;
    }

    public int getOrder() {
        return order;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeInt(this.entityId);
        dest.writeInt(this.order);
        dest.writeString(this.name);
        dest.writeString(this.from.toString());
        dest.writeString(this.to.toString());
    }

    protected Jump(Parcel in) {
        this.id = in.readInt();
        this.entityId = in.readInt();
        this.order = in.readInt();
        this.name = in.readString();
        this.from = new DateTime(in.readString());
        this.to = new DateTime(in.readString());
    }

    public static final Parcelable.Creator<Jump> CREATOR = new Parcelable.Creator<Jump>() {
        @Override public Jump createFromParcel(Parcel source) {
            return new Jump(source);
        }

        @Override public Jump[] newArray(int size) {
            return new Jump[size];
        }
    };
}
