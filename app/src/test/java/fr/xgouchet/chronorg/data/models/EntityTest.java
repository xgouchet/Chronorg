package fr.xgouchet.chronorg.data.models;

import net.danlew.android.joda.JodaTimeAndroid;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Duration;
import org.joda.time.Period;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.RuntimeEnvironment;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.joda.time.Duration.standardDays;

/**
 * NOTE : in these tests I use the UTC time zone, but for complete accuracy we should use
 * Pacific time zone
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

    private Entity marty;

    @Before
    public void setUp() {
        JodaTimeAndroid.init(RuntimeEnvironment.application);
        marty = new Entity(42, "Marty", "Martin Seamus McFly", MARTY_BIRTH, MARTY_DEATH);
    }

    @Test
    public void shouldGetAgesSimpleTimeline() {
        assertThat(marty.getAgesAtInstant(DOC_BROWN_DEATH))
                .hasSize(1)
                .contains(new Period(17, 4, 1, 6, 13, 33, 0, 0));
    }

    @Test
    public void shouldNotGetAgesBeforeBirthSimpleTimeline() {
        assertThat(marty.getAgesAtInstant(HILL_VALLEY_FOUNDATION))
                .isNotNull()
                .isEmpty();
    }


    @Test
    public void shouldNotGetAgesAfterDeathSimpleTimeline() {
        assertThat(marty.getAgesAtInstant(new DateTime(3015, 1, 1, 0, 0, 0, 0, DateTimeZone.UTC)))
                .isNotNull()
                .isEmpty();
    }

    @Test
    public void shouldGetMultipleAgesComplexTimeline() {
        // Given
        marty.jump(TWINE_PINE_MALL_1985_DEPARTURE, PEABODY_FARM_1955_LANDING);
        marty.jump(CLOCK_TOWER_1955_DEPARTURE, CLOCK_TOWER_1985_LANDING);

        // Then
        assertThat(marty.getAgesAtInstant(DOC_BROWN_DEATH))
                .hasSize(2)
                .contains(
                        new Period(17, 4, 1, 6, 13, 33, 0, 0),
                        new Period(17, 4, 3, 0, 5, 33, 0, 0));
    }

    @Test
    public void shouldNotGetAges_whenJumpedAround() {
        // Given
        marty.jump(LYON_ESTATE_1985_DEPARTURE, LYON_ESTATE_2015_LANDING);

        // Then
        assertThat(marty.getAgesAtInstant(new DateTime(2012, 9, 15, 3, 0, 0, 0, DateTimeZone.UTC)))
                .isNotNull()
                .isEmpty();
    }


    @Test
    public void shouldGetAges_whenJumpedForward() {
        // Given
        marty.jump(LYON_ESTATE_1985_DEPARTURE, LYON_ESTATE_2015_LANDING);

        // Then
        assertThat(marty.getAgesAtInstant(MARTY_JR_EXITS_PRISON))
                .hasSize(1)
                .contains(new Period(32, 4, 2, 0, 18, 31, 0, 0));
    }


    @Test
    public void shouldGetMultipleAges_whenJumpedForwardAndBack() {
        // Given
        marty.jump(LYON_ESTATE_1985_DEPARTURE, LYON_ESTATE_2015_LANDING);
        marty.jump(HILLDALE_2015_DEPARTURE, HILLDALE_1985_LANDING);

        // Then
        assertThat(marty.getAgesAtInstant(MARTY_JR_MEETS_GRIFF))
                .hasSize(2)
                .contains(new Period(17, 4, 1, 6, 23, 31, 0, 0),
                        new Period(47, 3, 5, 4, 9, 59, 0, 0));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrow_whenTryingImpossibleJump() {
        marty.jump(new DateTime(1985, 10, 26, 10, 40, 0, 0, DateTimeZone.UTC),
                new DateTime(2015, 10, 21, 18, 0, 0, 0, DateTimeZone.UTC));
        marty.jump(new DateTime(1985, 10, 27, 10, 40, 0, 0, DateTimeZone.UTC),
                new DateTime(2015, 10, 22, 18, 0, 0, 0, DateTimeZone.UTC));
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrow_whenTryingImpossibleJumpBeforeBirth() {
        marty.jump(new DateTime(1885, 10, 26, 10, 40, 0, 0, DateTimeZone.UTC),
                new DateTime(2015, 10, 21, 18, 0, 0, 0, DateTimeZone.UTC));
    }


    @Test(expected = UnsupportedOperationException.class)
    public void shouldThrow_whenTryingImpossibleJumpAfterDeath() {
        marty.jump(new DateTime(3050, 10, 26, 10, 40, 0, 0, DateTimeZone.UTC),
                new DateTime(2015, 10, 21, 18, 0, 0, 0, DateTimeZone.UTC));
    }

    @Test
    public void shouldGetInstantAtAgeSimpleTimeline() {
        assertThat(marty.getInstantAtAge(standardDays(365 * 30)))
                .isEqualTo(new DateTime(1998, 6, 5, 12, 0, 0, 0, DateTimeZone.UTC));
    }

    @Test
    public void shouldNotGetInstantAfterDeathSimpleTimeline() {
        assertThat(marty.getInstantAtAge(standardDays(365 * 300)))
                .isNull();
    }

    @Test
    public void shouldGetInstantAtAgeComplexTimeline() {
        // Given
        marty.jump(TWINE_PINE_MALL_1985_DEPARTURE, PEABODY_FARM_1955_LANDING);
        marty.jump(CLOCK_TOWER_1955_DEPARTURE, CLOCK_TOWER_1985_LANDING);
        marty.jump(LYON_ESTATE_1985_DEPARTURE, LYON_ESTATE_2015_LANDING);
        marty.jump(HILLDALE_2015_DEPARTURE, HILLDALE_1985_LANDING);
        marty.jump(HELL_1985_DEPARTURE, HILL_VALLEY_1955_LANDING);
        marty.jump(DESERT_1955_DEPARTURE, DESERT_1885_LANDING);
        marty.jump(SONASH_RAVINE_1885_DEPARTURE, EASTWOOD_RAVINE_1985_LANDING);

        // Then

        // 17 years, 4 months and 19 days old
        final Duration start = standardDays((365 * 17) + (30 * 4) + 19);
        assertThat(marty.getInstantAtAge(start))
                .isEqualTo(new DateTime(1985, 10, 25, 12, 0, 0, 0, DateTimeZone.UTC));

        // Exactly two days later, he's in 1955
        assertThat(marty.getInstantAtAge(standardDays(start.getStandardDays() + 2)))
                .isEqualTo(new DateTime(1955, 11, 6, 16, 40, 0, 0, DateTimeZone.UTC));

        // Exactly two weeks later, he's in 1885
        assertThat(marty.getInstantAtAge(standardDays(start.getStandardDays() + 14)))
                .isEqualTo(new DateTime(1885, 9, 2, 16, 19, 0, 0, DateTimeZone.UTC));


        // Exactly three weeks later, he's four days later...
        assertThat(marty.getInstantAtAge(standardDays(start.getStandardDays() + 21)))
                .isEqualTo(new DateTime(1985, 10, 29, 18, 19, 0, 0, DateTimeZone.UTC));

    }

    // TODO test parcelable
}