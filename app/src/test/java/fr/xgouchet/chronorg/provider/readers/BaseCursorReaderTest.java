package fr.xgouchet.chronorg.provider.readers;

import android.database.Cursor;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class BaseCursorReaderTest {


    @Mock Cursor mockCursor;
    @Mock Object mockObject;
    BaseCursorReader<Object> spiedCursorReader;

    @Before
    public void setUp() {
        initMocks(this);
        spiedCursorReader = spy(new BaseCursorReader<Object>(mockCursor) {


            @Override protected void cacheIndices() {
            }

            @NonNull @Override public Object instantiate() {
                return mockObject;
            }

            @Override public void fill(@NonNull Object entity) {

            }
        });
    }

    @Test
    public void should_instantiate_and_fill() {
        // When
        spiedCursorReader.instantiateAndFill();

        // Then
        verify(spiedCursorReader).instantiate();
        verify(spiedCursorReader).fill(mockObject);
    }

    @Test
    public void should_read_column_index() {
        // Given
        final String columnName = "foo";
        final int columnIndex = 42;
        when(mockCursor.getColumnIndex(anyString())).thenReturn(columnIndex);

        // When
        int result = spiedCursorReader.getIndex(columnName);

        // Then
        assertThat(result).isEqualTo(columnIndex);
        verify(mockCursor).getColumnIndex(columnName);
    }

    @Test
    public void should_read_int_value() {
        // Given
        final int columnIndex = 42;
        final int value = 815;
        when(mockCursor.getInt(anyInt())).thenReturn(value);

        // When
        int result = spiedCursorReader.readInt(columnIndex);

        // Then
        assertThat(result).isEqualTo(value);
        verify(mockCursor).getInt(columnIndex);
    }

    @Test
    public void should_read_string_value() {
        // Given
        final int columnIndex = 42;
        final String value = "foo";
        when(mockCursor.getString(anyInt())).thenReturn(value);

        // When
        String result = spiedCursorReader.readString(columnIndex);

        // Then
        assertThat(result).isEqualTo(value);
        verify(mockCursor).getString(columnIndex);
    }
}