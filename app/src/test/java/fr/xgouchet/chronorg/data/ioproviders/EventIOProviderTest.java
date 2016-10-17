package fr.xgouchet.chronorg.data.ioproviders;

import android.database.Cursor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;
import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.data.queriers.ContentQuerier;
import fr.xgouchet.chronorg.data.queriers.EventContentQuerier;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.readers.EventCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.data.writers.EventContentValuesWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class EventIOProviderTest {

    EventIOProvider provider;

    @Before
    public void setUp() {
        initMocks(this);
        provider = new EventIOProvider();
    }

    @Test
    public void shouldProvideWriter() {
        // When
        BaseContentValuesWriter<Event> writer = provider.provideWriter();

        // Then
        assertThat(writer).isInstanceOf(EventContentValuesWriter.class);
    }

    @Test
    public void shouldProvideReader() {
        // Given
        Cursor mockCursor = mock(Cursor.class);

        // When
        BaseCursorReader<Event> reader = provider.provideReader(mockCursor);

        // Then
        assertThat(reader).isExactlyInstanceOf(EventCursorReader.class);
        assertThat(reader.getCursor()).isSameAs(mockCursor);
    }

    @Test
    public void shouldProvideQuerier() {
        // When
        ContentQuerier<Event> reader = provider.provideQuerier();

        // Then
        assertThat(reader).isExactlyInstanceOf(EventContentQuerier.class);
    }
}