package fr.xgouchet.chronorg.data.models;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.Period;
import org.joda.time.ReadableInstant;

import java.util.LinkedList;
import java.util.List;

/**
 * An Entity is an entity with the ability to jump through time
 *
 * @author Xavier Gouchet
 */
public class Entity {

    private int projectId;

    @NonNull
    private String name;
    @Nullable
    private String description;

    @NonNull
    private ReadableInstant birth;
    @Nullable
    private ReadableInstant death;

    @NonNull
    private final List<Jump> jumps;

    public Entity() {
        name = "‽";
        description = null;
        birth = new DateTime("1970-00-00T00:00:00Z");
        death = null;
        jumps = new LinkedList<>();
    }

    public Entity(int projectId,
                  @NonNull String name,
                  @Nullable String description,
                  @NonNull ReadableInstant birth,
                  @Nullable ReadableInstant death) {
        this.projectId = projectId;
        this.name = name;
        this.description = description;
        this.birth = birth;
        this.death = death;
        jumps = new LinkedList<>();
    }


    public void jump(@NonNull ReadableInstant from, @NonNull ReadableInstant to) {

        // Safe check, can the jump occur ?
        if (jumps.isEmpty()) {
            if (from.isBefore(birth)) {
                throw new UnsupportedOperationException("Can't jump from a date before birth");
            }
            if ((death != null) && from.isAfter(death)) {
                throw new UnsupportedOperationException("Can't jump from a date after death");
            }
        } else {
            Jump last = jumps.get(jumps.size() - 1);
            if (from.isBefore(last.to)) {
                throw new UnsupportedOperationException("Can't jump from a date before last jump's landing");
            }
        }

        jumps.add(new Jump(from.toInstant(), to.toInstant()));
    }

    @NonNull
    public List<Period> getAgesAtInstant(@NonNull ReadableInstant instant) {

        List<Period> ages = new LinkedList<>();
        Period ageAtLastInstant = new Period(0, 0, 0, 0, 0, 0, 0, 0);
        ReadableInstant lastInstant = new Instant(birth);

        // Go through each jumps
        for (Jump jump : jumps) {
            if (lastInstant.isBefore(instant) && jump.from.isAfter(instant)) {
                Period sinceLastInstant = new Period(lastInstant, instant);
                ages.add(ageAtLastInstant.plus(sinceLastInstant).normalizedStandard());
            }

            Period betweenJumps = new Period(lastInstant, jump.from);
            ageAtLastInstant = ageAtLastInstant.plus(betweenJumps);
            lastInstant = jump.to;
        }

        // after the last jump
        if (lastInstant.isBefore(instant)) {
            if ((death == null) || death.isAfter(instant)) {
                Period sinceLastInstant = new Period(lastInstant, instant);
                ages.add(ageAtLastInstant.plus(sinceLastInstant).normalizedStandard());
            }
        }


        return ages;
    }

    @Nullable
    public ReadableInstant getInstantAtAge(@NonNull Duration duration) {
        Duration durationLeft = duration;
        ReadableInstant lastInstant = birth;
        Duration fragmentDuration;

        // follow jumps
        for (Jump jump : jumps) {
            fragmentDuration = new Duration(lastInstant, jump.from);
            if (durationLeft.isShorterThan(fragmentDuration)) {
                return lastInstant.toInstant().plus(durationLeft);
            }

            durationLeft = durationLeft.minus(fragmentDuration);
            lastInstant = jump.to;
        }

        // check death
        if (death != null) {
            fragmentDuration = new Duration(lastInstant, death);
        } else {
            fragmentDuration = durationLeft.plus(1);
        }

        if (durationLeft.isShorterThan(fragmentDuration)) {
            return lastInstant.toInstant().plus(durationLeft);
        } else {
            return null;
        }
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

    @Nullable public ReadableInstant getDeath() {
        return death;
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

    public void setDeath(@Nullable ReadableInstant death) {
        this.death = death;
    }
}
