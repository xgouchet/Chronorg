package fr.xgouchet.chronorg.data.models;

import android.support.annotation.ColorInt;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import org.joda.time.ReadableInstant;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.LinkedList;
import java.util.List;

/**
 * @author Xavier Gouchet
 */
public class TimelineShard {

    public static final int TYPE_START = 1;
    public static final int TYPE_END = 2;
    public static final int TYPE_EVENT = 3;

    @IntDef({TYPE_START, TYPE_END, TYPE_EVENT})
    @Retention(RetentionPolicy.CLASS)
    public @interface Type {
    }

    @NonNull private final ReadableInstant instant;
    @NonNull private final int[] ongoingSegments;
    @Type private final int type;
    @ColorInt private final int color;
    @NonNull private final String legend;
    private final int position;

    /*package*/ TimelineShard(@Type int type,
                              @ColorInt int color,
                              @NonNull ReadableInstant instant,
                              @NonNull String legend,
                              @NonNull int[] ongoingSegments,
                              int position) {
        this.instant = instant;
        this.ongoingSegments = ongoingSegments;
        this.type = type;
        this.color = color;
        this.legend = legend;
        this.position = position;
    }

    @NonNull public int[] getOngoingSegments() {
        return ongoingSegments;
    }

    public int getType() {
        return type;
    }

    public int getColor() {
        return color;
    }

    @NonNull public String getLegend() {
        return legend;
    }

    @NonNull public ReadableInstant getInstant() {
        return instant;
    }

    public int getPosition() {
        return position;
    }

    public static class Builder {

        @Type private final int type;
        @ColorInt private final int color;
        @NonNull private final ReadableInstant instant;
        @NonNull private String legend = "";

        List<Integer> ongoingSegments = new LinkedList<>();
        private int position;

        public Builder(@Type int type,
                       @ColorInt int color,
                       @NonNull ReadableInstant instant) {
            this.type = type;
            this.color = color;
            this.instant = instant;
        }

        public Builder withLegend(@NonNull String legend) {
            this.legend = legend;
            return this;
        }

        public Builder withOngoingSegment(@Nullable Segment segment) {
            this.ongoingSegments.add(segment == null ? 0x00000000 : segment.getColor());
            return this;
        }

        public Builder withOngoingSegmentColor(@ColorInt int color) {
            this.ongoingSegments.add(color);
            return this;
        }

        public Builder withPosition(int i) {
            this.position = i;
            return this;
        }

        public TimelineShard build() {
            int[] ongoing = new int[ongoingSegments.size()];
            for (int i = 0; i < ongoing.length; i++) {
                ongoing[i] = ongoingSegments.get(i);
            }
            return new TimelineShard(type, color, instant, legend, ongoing, position);
        }
    }

    @Override public String toString() {
        StringBuilder builder = new StringBuilder();

        for (int i = 0; i < ongoingSegments.length; i++) {
            if (position == i)
                if (type == TYPE_END)
                    builder.append('†');
                else
                    builder.append('*');
            else if (ongoingSegments[i] == 0)
                builder.append(' ');
            else
                builder.append('|');
        }

        builder.append("    ");
        builder.append(instant.toString());
        builder.append(legend);


        return builder.toString();
    }
}
