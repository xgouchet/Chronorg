package fr.xgouchet.chronorg.data.ioproviders;

import android.database.Cursor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.data.queriers.ContentQuerier;
import fr.xgouchet.chronorg.data.queriers.JumpContentQuerier;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.readers.JumpCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.data.writers.JumpContentValuesWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class JumpIOProviderTest {

    JumpIOProvider provider;

    @Before
    public void setUp() {
        initMocks(this);
        provider = new JumpIOProvider();
    }

    @Test
    public void shouldProvideWriter() {
        // When
        BaseContentValuesWriter<Jump> writer = provider.provideWriter();

        // Then
        assertThat(writer).isInstanceOf(JumpContentValuesWriter.class);
    }

    @Test
    public void shouldProvideReader() {
        // Given
        Cursor mockCursor = mock(Cursor.class);

        // When
        BaseCursorReader<Jump> reader = provider.provideReader(mockCursor);

        // Then
        assertThat(reader).isExactlyInstanceOf(JumpCursorReader.class);
        assertThat(reader.getCursor()).isSameAs(mockCursor);
    }

    @Test
    public void shouldProvideQuerier() {
        // When
        ContentQuerier<Jump> reader = provider.provideQuerier();

        // Then
        assertThat(reader).isExactlyInstanceOf(JumpContentQuerier.class);
    }
}