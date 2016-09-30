package fr.xgouchet.chronorg.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.Duration;
import org.joda.time.Instant;
import org.joda.time.Period;
import org.joda.time.ReadableInstant;

import java.util.LinkedList;
import java.util.List;

/**
 * A Chrono Entity is an entity with the ability to jump through time
 *
 * @author Xavier Gouchet
 */
public class ChronoEntity {

    @NonNull
    private final ReadableInstant birth;
    @Nullable
    private final ReadableInstant death;

    private final List<ChronoJump> jumps;

    public ChronoEntity(@NonNull ReadableInstant birth) {
        this(birth, null);
    }

    public ChronoEntity(@NonNull ReadableInstant birth, @Nullable ReadableInstant death) {
        this.birth = birth;
        this.death = death;
        jumps = new LinkedList<>();
    }


    public void jump(ReadableInstant from, ReadableInstant to) {

        // Safe check, can the jump occur ?
        if (jumps.isEmpty()) {
            if (from.isBefore(birth)) {
                throw new UnsupportedOperationException("Can't jump from a date before birth");
            }
            if ((death != null) && from.isAfter(death)) {
                throw new UnsupportedOperationException("Can't jump from a date after death");
            }
        } else {
            ChronoJump last = jumps.get(jumps.size() - 1);
            if (from.isBefore(last.to)) {
                throw new UnsupportedOperationException("Can't jump from a date before last jump's landing");
            }
        }

        jumps.add(new ChronoJump(from.toInstant(), to.toInstant()));
    }

    @NonNull
    public List<Period> getAgesAtInstant(@NonNull ReadableInstant instant) {

        List<Period> ages = new LinkedList<>();
        Period ageAtLastInstant = new Period(0, 0, 0, 0, 0, 0, 0, 0);
        ReadableInstant lastInstant = new Instant(birth);

        // Go through each jumps
        for (ChronoJump jump : jumps) {
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
        for (ChronoJump jump : jumps) {
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

    private static class ChronoJump {
        @NonNull final ReadableInstant from;
        @NonNull final ReadableInstant to;

        private ChronoJump(@NonNull ReadableInstant from, @NonNull ReadableInstant to) {
            this.from = from;
            this.to = to;
        }
    }
}
