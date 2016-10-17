package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;
import fr.xgouchet.chronorg.data.ioproviders.EventIOProvider;
import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.data.readers.EventCursorReader;
import fr.xgouchet.chronorg.data.writers.EventContentValuesWriter;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.functions.Action1;

import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.verifyZeroInteractions;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class EventContentQuerierTest {

    @Mock ContentResolver contentResolver;
    @Mock EventIOProvider provider;
    @Mock Action1<Event> action;
    @Mock EventCursorReader reader;
    @Mock EventContentValuesWriter writer;
    @Mock Cursor cursor;
    @Mock ContentValues contentValues;

    private EventContentQuerier querier;

    @Before
    public void setUp() {
        initMocks(this);
        when(provider.provideReader(any(Cursor.class)))
                .thenReturn(reader);

        when(provider.provideWriter())
                .thenReturn(writer);
        querier = new EventContentQuerier(provider);
    }


    @Test
    public void shouldQueryInProject() {
        // Given
        int entityId = 42;
        Event mock1 = mock(Event.class);
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenReturn(mock1, (Event) null);


        // When
        querier.queryInProject(contentResolver, action, entityId);

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.EVENTS_URI), isNull(String[].class),
                eq("project_id=?"), eq(new String[]{"42"}), eq("instant ASC"));
        verify(provider).provideReader(same(cursor));
        verify(action).call(mock1);
        verify(cursor).close();
        verifyNoMoreInteractions(action);
    }

    @Test
    public void shouldQueryInProjectWithException() {
        // Given
        int entityId = 42;
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenThrow(new RuntimeException());


        // When
        try {
            querier.queryInProject(contentResolver, action, entityId);
            fail("Should leak exception");
        } catch (RuntimeException ignore) {
        }

        // Then
        verify(contentResolver).query(eq(ChronorgSchema.EVENTS_URI), isNull(String[].class),
                eq("project_id=?"), eq(new String[]{"42"}), eq("instant ASC"));
        verify(provider).provideReader(same(cursor));
        verify(cursor).close();
        verifyZeroInteractions(action);
    }

}