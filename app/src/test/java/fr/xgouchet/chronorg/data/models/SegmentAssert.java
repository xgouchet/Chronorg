package fr.xgouchet.chronorg.data.models;

import android.support.annotation.Nullable;

import org.assertj.core.api.AbstractAssert;
import org.assertj.core.api.Assertions;
import org.joda.time.ReadableInstant;

/**
 * @author Xavier Gouchet
 */
public class SegmentAssert extends AbstractAssert<SegmentAssert, Segment> {


    public static SegmentAssert assertThat(@Nullable Segment actual) {
        return new SegmentAssert(actual);
    }

    protected SegmentAssert(Segment actual) {
        super(actual, SegmentAssert.class);
    }

    public SegmentAssert hasFrom(ReadableInstant expected) {
        isNotNull();

        ReadableInstant actualFrom = actual.getFrom();
        Assertions.assertThat(actualFrom)
                .overridingErrorMessage("Expected from <%s> but was <%s>.", expected.toString(), actualFrom.toString())
                .isEqualTo(expected);

        return this;
    }

    public SegmentAssert hasLegendFrom(String expected) {
        isNotNull();

        String actualLegendFrom = actual.getLegendFrom();
        Assertions.assertThat(actualLegendFrom)
                .overridingErrorMessage("Expected legend from <%s> but was <%s>.", expected, actualLegendFrom)
                .isEqualTo(expected);

        return this;
    }

    public SegmentAssert hasTo(ReadableInstant expected) {
        isNotNull();

        ReadableInstant actualTo = actual.getTo();
        Assertions.assertThat(actualTo)
                .overridingErrorMessage("Expected to <%s> but was <%s>.", expected.toString(), actualTo.toString())
                .isEqualTo(expected);

        return this;
    }

    public SegmentAssert hasLegendTo(String expected) {
        isNotNull();

        String actualLegendTo = actual.getLegendTo();
        Assertions.assertThat(actualLegendTo)
                .overridingErrorMessage("Expected legend to <%s> but was <%s>.", expected, actualLegendTo)
                .isEqualTo(expected);

        return this;
    }

    public SegmentAssert hasColor(int expected) {
        isNotNull();

        int actualColor = actual.getColor();
        Assertions.assertThat(actualColor)
                .overridingErrorMessage("Expected color <%d> but was <%d>.", expected, actualColor)
                .isEqualTo(expected);

        return this;
    }
}
