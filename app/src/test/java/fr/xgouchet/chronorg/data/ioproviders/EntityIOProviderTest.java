package fr.xgouchet.chronorg.data.ioproviders;

import android.database.Cursor;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.queriers.BaseContentQuerier;
import fr.xgouchet.chronorg.data.queriers.EntityContentQuerier;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.readers.EntityCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import fr.xgouchet.chronorg.data.writers.EntityContentValuesWriter;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class EntityIOProviderTest {

    EntityIOProvider provider;

    @Before
    public void setUp() {
        provider = new EntityIOProvider();
    }

    @Test
    public void shouldProvideWriter() {
        // When
        BaseContentValuesWriter<Entity> writer = provider.provideWriter();

        // Then
        assertThat(writer).isInstanceOf(EntityContentValuesWriter.class);
    }

    @Test
    public void shouldProvideReader() {
        // Given
        Cursor mockCursor = mock(Cursor.class);

        // When
        BaseCursorReader<Entity> reader = provider.provideReader(mockCursor);

        // Then
        assertThat(reader).isExactlyInstanceOf(EntityCursorReader.class);
        assertThat(reader.getCursor()).isSameAs(mockCursor);
    }

    @Test
    public void shouldProvideQuerier() {
        // When
        BaseContentQuerier<Entity> reader = provider.provideQuerier();

        // Then
        assertThat(reader).isExactlyInstanceOf(EntityContentQuerier.class);
    }
}