package fr.xgouchet.chronorg.data.models;

import android.graphics.Color;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;

import static fr.xgouchet.chronorg.data.models.SegmentAssert.assertThat;
import static org.assertj.core.api.Assertions.assertThat;

/**
 * NOTE : in these tests I use the UTC time zone,
 * but for complete accuracy we should use the Pacific time zone
 *
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class EntityTest {

    public static final DateTime TWINE_PINE_MALL_1985_DEPARTURE = new DateTime(1985, 10, 26, 1, 35, 0, 0, DateTimeZone.UTC);
    public static final DateTime PEABODY_FARM_1955_LANDING = new DateTime(1955, 11, 5, 6, 15, 0, 0, DateTimeZone.UTC);
    public static final DateTime CLOCK_TOWER_1955_DEPARTURE = new DateTime(1955, 11, 12, 22, 4, 0, 0, DateTimeZone.UTC);
    public static final DateTime CLOCK_TOWER_1985_LANDING = new DateTime(1985, 10, 26, 1, 24, 0, 0, DateTimeZone.UTC);
    public static final DateTime LYON_ESTATE_1985_DEPARTURE = new DateTime(1985, 10, 26, 11, 00, 0, 0, DateTimeZone.UTC);
    public static final DateTime LYON_ESTATE_2015_LANDING = new DateTime(2015, 10, 21, 16, 29, 0, 0, DateTimeZone.UTC);
    public static final DateTime HILLDALE_2015_DEPARTURE = new DateTime(2015, 10, 21, 19, 28, 0, 0, DateTimeZone.UTC);
    public static final DateTime HILLDALE_1985_LANDING = new DateTime(1985, 10, 26, 9, 0, 0, 0, DateTimeZone.UTC);

    public static final DateTime HELL_1985_DEPARTURE = new DateTime(1985, 10, 27, 2, 42, 0, 0, DateTimeZone.UTC);
    public static final DateTime HILL_VALLEY_1955_LANDING = new DateTime(1955, 11, 12, 6, 0, 0, 0, DateTimeZone.UTC);

    public static final DateTime LIGHTNING_1955_DEPARTURE = new DateTime(1955, 11, 12, 21, 44, 0, 0, DateTimeZone.UTC);
    public static final DateTime LIGHTNING_1885_LANDING = new DateTime(1885, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC);

    public static final DateTime DESERT_1955_DEPARTURE = new DateTime(1955, 11, 16, 10, 0, 0, 0, DateTimeZone.UTC);
    public static final DateTime DESERT_1885_LANDING = new DateTime(1885, 9, 2, 8, 0, 0, 0, DateTimeZone.UTC);

    public static final DateTime SONASH_RAVINE_1885_DEPARTURE = new DateTime(1885, 9, 7, 9, 0, 0, 0, DateTimeZone.UTC);
    public static final DateTime EASTWOOD_RAVINE_1985_LANDING = new DateTime(1985, 10, 27, 11, 0, 0, 0, DateTimeZone.UTC);

    public static final DateTime MARTY_BIRTH = new DateTime(1968, 6, 12, 12, 0, 0, 0, DateTimeZone.UTC);
    public static final DateTime MARTY_DEATH = new DateTime(2091, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC);
    public static final DateTime DOC_BROWN_DEATH = new DateTime(1985, 10, 26, 1, 33, 0, 0, DateTimeZone.UTC);

    public static final DateTime MARTY_JR_EXITS_PRISON = new DateTime(2030, 10, 22, 12, 0, 0, 0, DateTimeZone.UTC);
    public static final DateTime MARTY_JR_MEETS_GRIFF = new DateTime(2015, 10, 21, 17, 0, 0, 0, DateTimeZone.UTC);

    public static final DateTime HILL_VALLEY_FOUNDATION = new DateTime(1865, 9, 5, 12, 0, 0, 0, DateTimeZone.UTC);

    private static final int COLOR = Color.RED;
    public static final String MARTY = "Marty";

    private Entity marty;

    @Before
    public void setUp() {
        JodaTimeAndroid.init(RuntimeEnvironment.application);
        marty = new Entity(42, MARTY, MARTY_BIRTH, MARTY_DEATH, COLOR);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrow_whenTryingImpossibleJump() {
        marty.jump("First Jump",
                new DateTime(1985, 10, 26, 10, 40, 0, 0, DateTimeZone.UTC),
                new DateTime(2015, 10, 21, 18, 0, 0, 0, DateTimeZone.UTC));
        marty.jump("Impossible Jump",
                new DateTime(1985, 10, 27, 10, 40, 0, 0, DateTimeZone.UTC),
                new DateTime(2015, 10, 22, 18, 0, 0, 0, DateTimeZone.UTC));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrow_whenTryingImpossibleJumpBeforeBirth() {
        marty.jump("Impossible Jump",
                new DateTime(1885, 10, 26, 10, 40, 0, 0, DateTimeZone.UTC),
                new DateTime(2015, 10, 21, 18, 0, 0, 0, DateTimeZone.UTC));
    }


    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrow_whenTryingImpossibleJumpAfterDeath() {
        marty.jump("Impossible Jump",
                new DateTime(3050, 10, 26, 10, 40, 0, 0, DateTimeZone.UTC),
                new DateTime(2015, 10, 21, 18, 0, 0, 0, DateTimeZone.UTC));
    }


    @Test
    public void shouldBuildSegmentsComplexTimeline() {
        // Given
        marty.jump("Jump 1", TWINE_PINE_MALL_1985_DEPARTURE, PEABODY_FARM_1955_LANDING);
        marty.jump("Jump 2", CLOCK_TOWER_1955_DEPARTURE, CLOCK_TOWER_1985_LANDING);
        marty.jump("Jump 3", LYON_ESTATE_1985_DEPARTURE, LYON_ESTATE_2015_LANDING);
        marty.jump("Jump 4", HILLDALE_2015_DEPARTURE, HILLDALE_1985_LANDING);
        marty.jump("Jump 5", HELL_1985_DEPARTURE, HILL_VALLEY_1955_LANDING);
        marty.jump("Jump 6", DESERT_1955_DEPARTURE, DESERT_1885_LANDING);
        marty.jump("Jump 7", SONASH_RAVINE_1885_DEPARTURE, EASTWOOD_RAVINE_1985_LANDING);

        // When
        Segment[] segments = marty.timelineAsSegments();

        // Then
        assertThat(segments).hasSize(8);
        assertThat(segments[0])
                .hasFrom(MARTY_BIRTH)
                .hasLegendFrom(MARTY + " *")
                .hasTo(TWINE_PINE_MALL_1985_DEPARTURE)
                .hasLegendTo("Jump 1")
                .hasColor(COLOR);

        assertThat(segments[3])
                .hasFrom(LYON_ESTATE_2015_LANDING)
                .hasLegendFrom("Jump 3")
                .hasTo(HILLDALE_2015_DEPARTURE)
                .hasLegendTo("Jump 4")
                .hasColor(COLOR);
    }

}