package fr.xgouchet.chronorg.provider.writers;

import android.content.ContentValues;
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
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class BaseContentValuesWriterTest {
    @Mock Cursor mockCursor;
    @Mock Object mockObject;
    BaseContentValuesWriter<Object> spiedWriter;

    @Before
    public void setUp() {
        initMocks(this);
        spiedWriter = spy(new BaseContentValuesWriter<Object>() {
            @Override
            public void fillContentValues(@NonNull ContentValues cv, @NonNull Object entity) {

            }
        });
    }

    @Test
    public void should_transform_to_content_values() {
        // Given
        Object entity = new Object();

        // When
        ContentValues cv = spiedWriter.toContentValues(entity);

        // Then
        assertThat(cv).isNotNull();
        verify(spiedWriter).fillContentValues(cv, entity);
    }

}