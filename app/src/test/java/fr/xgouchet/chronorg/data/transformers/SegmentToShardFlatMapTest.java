package fr.xgouchet.chronorg.data.transformers;

import android.graphics.Color;

import org.joda.time.DateTime;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Arrays;
import java.util.List;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;
import fr.xgouchet.chronorg.data.models.Segment;
import fr.xgouchet.chronorg.data.models.Shard;
import rx.Observable;
import rx.observers.TestSubscriber;

/**
 * @author Xavier Gouchet
 */
@SuppressWarnings("ArraysAsListWithZeroOrOneArgument")
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class SegmentToShardFlatMapTest {


    public static final DateTime INSTANT_0 = new DateTime("1983-01-01T00:00:00Z");
    public static final DateTime INSTANT_1 = new DateTime("1984-01-01T00:00:00Z");
    public static final DateTime INSTANT_2 = new DateTime("1985-01-01T00:00:00Z");
    public static final DateTime INSTANT_3 = new DateTime("1986-01-01T00:00:00Z");
    public static final DateTime INSTANT_4 = new DateTime("1987-01-01T00:00:00Z");
    public static final DateTime INSTANT_5 = new DateTime("1988-01-01T00:00:00Z");
    public static final DateTime INSTANT_6 = new DateTime("1989-01-01T00:00:00Z");
    public static final DateTime INSTANT_7 = new DateTime("1990-01-01T00:00:00Z");

    public static final String LEGEND_0 = "foo";
    public static final String LEGEND_1 = "bar";
    public static final String LEGEND_2 = "baz";

    private SegmentToShardFlatMap transformer;
    private TestSubscriber<Object> subscriber;

    @Before
    public void setUp() {
        transformer = new SegmentToShardFlatMap();
        subscriber = new TestSubscriber<>();
    }

    @Test
    public void shouldCreateShardsFromSingleSegment() {
        // Given
        Segment segment = new Segment(INSTANT_0, LEGEND_0, INSTANT_1, LEGEND_1, Color.RED);

        // When
        Observable<Shard> observable = transformer.call(Arrays.asList(segment));
        observable.subscribe(subscriber);

        // Then
        subscriber.assertNoErrors();
        subscriber.assertCompleted();
        List result = subscriber.getOnNextEvents();
    }
}