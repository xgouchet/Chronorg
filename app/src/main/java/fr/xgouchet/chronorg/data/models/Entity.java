package fr.xgouchet.chronorg.data.models;

import android.graphics.Color;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.joda.time.ReadableInstant;

import java.util.LinkedList;
import java.util.List;

/**
 * An Entity is an entity with the ability to jump through time
 *
 * @author Xavier Gouchet
 */
public class Entity implements Parcelable {

    private int id;
    private int projectId;

    @NonNull private String name;
    @Nullable private String description;

    @NonNull private ReadableInstant birth;
    @NonNull private ReadableInstant death;

    @ColorInt private int color;
    @NonNull private final List<Jump> jumps;

    public Entity() {
        id = -1;
        name = "‽";
        description = null;
        birth = new DateTime("1970-01-01T00:00:00Z");
        death = new DateTime("2038-01-19T03:14:17Z");
        color = Color.rgb(0xF6, 0x40, 0x2C);
        jumps = new LinkedList<>();
    }

    public Entity(int projectId,
                  @NonNull String name,
                  @Nullable String description,
                  @NonNull ReadableInstant birth,
                  @NonNull ReadableInstant death) {
        id = -1;
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.birth = birth;
        this.death = death;
        this.color = Color.RED;
        jumps = new LinkedList<>();
    }


    public void jump(@NonNull Jump jump) {

        final ReadableInstant from = jump.getFrom();

        // Safe check, can the jump occur ?
        if (jumps.isEmpty()) {
            if (from.isBefore(birth)) {
                throw new UnsupportedOperationException("Can't jump from a date before birth");
            }
            if (from.isAfter(death)) {
                throw new UnsupportedOperationException("Can't jump from a date after death");
            }
        } else {
            Jump last = jumps.get(jumps.size() - 1);
            if (from.isBefore(last.getTo())) {
                throw new UnsupportedOperationException("Can't jump from a date before last jump's landing");
            }
        }

        jumps.add(jump);
    }

    public void jump(@NonNull ReadableInstant from, @NonNull ReadableInstant to) {
        jump(new Jump(from, to));
    }

    public Segment[] timelineAsSegments() {
        Segment[] segments = new Segment[1 + jumps.size()];
        int index = 0;
        ReadableInstant lastInstant = birth;
        String lastLegend = name + " *";

        // follow jumps
        for (Jump jump : jumps) {
            if (jump.getFrom().isBefore(lastInstant)) {
                throw new IllegalStateException("Illegal jump at " + jump.getFrom() +
                        " when last instant was " + lastInstant);
            }
            segments[index] = new Segment(lastInstant, lastLegend, jump.getFrom(), jump.getName(), color);
            index++;
            lastInstant = jump.getTo();
            lastLegend = jump.getName();
        }

        // till death do us part
        segments[index] = new Segment(lastInstant, lastLegend, death, name + " †", color);

        return segments;
    }

    @NonNull
    public List<Period> getAgesAtInstant(@NonNull ReadableInstant instant) {

        List<Period> ages = new LinkedList<>();
        Period cumulativeAge = new Period(0, 0, 0, 0, 0, 0, 0, 0);

        for (Segment segment : timelineAsSegments()) {
            if (segment.contains(instant)) {
                Period sincePeriodStart = new Period(segment.getFrom(), instant);
                ages.add(cumulativeAge.plus(sincePeriodStart).normalizedStandard());
            }

            cumulativeAge = cumulativeAge.plus(segment.period());
        }

        return ages;
    }

    @Nullable
    public ReadableInstant getInstantAtAge(@NonNull Duration duration) {

        Duration durationLeft = duration;
        Duration segmentDuration;

        for (Segment segment : timelineAsSegments()) {
            segmentDuration = segment.duration();
            if (durationLeft.isShorterThan(segmentDuration)) {
                return segment.getFrom().toInstant().plus(durationLeft);
            }

            durationLeft = durationLeft.minus(segmentDuration);
        }

        return null;
    }

    public int getId() {
        return id;
    }

    public int getProjectId() {
        return projectId;
    }

    @NonNull public String getName() {
        return name;
    }

    @Nullable public String getDescription() {
        return description;
    }

    @NonNull public ReadableInstant getBirth() {
        return birth;
    }

    @NonNull public ReadableInstant getDeath() {
        return death;
    }

    @ColorInt public int getColor() {
        return color;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public void setName(@NonNull String name) {
        this.name = name;
    }

    public void setDescription(@Nullable String description) {
        this.description = description;
    }

    public void setBirth(@NonNull ReadableInstant birth) {
        this.birth = birth;
    }

    public void setBirth(@NonNull String birth) {
        this.birth = new DateTime(birth);
    }

    public void setDeath(@NonNull ReadableInstant death) {
        this.death = death;
    }

    public void setDeath(@NonNull String death) {
        this.death = new DateTime(death);
    }

    public void setColor(@ColorInt int color) {
        this.color = color;
    }

    @Override public int describeContents() {
        return 0;
    }

    @Override public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(id);
        dest.writeInt(projectId);
        dest.writeString(name);
        dest.writeString(description);
        dest.writeString(birth.toString());
        dest.writeString(death.toString());
        dest.writeInt(color);
    }

    protected Entity(Parcel in) {
        id = in.readInt();
        projectId = in.readInt();
        name = in.readString();
        description = in.readString();
        birth = new DateTime(in.readString());
        death = new DateTime(in.readString());
        color = in.readInt();
        jumps = new LinkedList<>();
    }

    public static final Parcelable.Creator<Entity> CREATOR = new Parcelable.Creator<Entity>() {
        @Override public Entity createFromParcel(Parcel source) {
            return new Entity(source);
        }

        @Override public Entity[] newArray(int size) {
            return new Entity[size];
        }
    };
}
