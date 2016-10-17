package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;
import fr.xgouchet.chronorg.data.ioproviders.IOProvider;
import fr.xgouchet.chronorg.data.readers.BaseCursorReader;
import fr.xgouchet.chronorg.data.writers.BaseContentValuesWriter;
import rx.functions.Action1;

import static org.assertj.core.api.Assertions.assertThat;
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
public class BaseContentQuerierTest {

    public static final String SELECT_BY_ID = "foo=?";
    public static final String DEFAULT_ORDER = "bar ASC";
    public static final Uri FAKE_URI = Uri.parse("http://foo.com/bar/baz/spam/42");
    public static final int FAKE_ID = 42;
    public static final String FAKE_ID_STR = "42";

    @Mock ContentResolver contentResolver;
    @Mock IOProvider<Object> provider;
    @Mock BaseCursorReader<Object> reader;
    @Mock BaseContentValuesWriter<Object> writer;
    @Mock Action1<Object> action;
    @Mock Cursor cursor;
    @Mock ContentValues contentValues;

    @Mock BaseContentQuerier<Object> contentQuerier;

    private BaseContentQuerier<Object> proxyQuerier;


    @Before
    public void setUp() {
        initMocks(this);

        when(provider.provideReader(any(Cursor.class)))
                .thenReturn(reader);

        when(provider.provideWriter())
                .thenReturn(writer);

        when(contentQuerier.getUri()).thenReturn(FAKE_URI);
        when(contentQuerier.defaultOrder()).thenReturn(DEFAULT_ORDER);
        when(contentQuerier.getId(any())).thenReturn(FAKE_ID);
        when(contentQuerier.selectById()).thenReturn(SELECT_BY_ID);


        proxyQuerier = new ProxyContentQuerier(contentQuerier, provider);
    }

    @Test
    public void shouldQueryAll() {
        // Given
        when(cursor.getCount()).thenReturn(2);
        when(cursor.moveToNext()).thenReturn(true, true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        final Object mock1 = mock(Object.class);
        final Object mock2 = mock(Object.class);
        when(reader.instantiateAndFill()).thenReturn(mock1, mock2, null);

        // When
        proxyQuerier.queryAll(contentResolver, action);

        // Then
        verify(contentResolver).query(eq(FAKE_URI), isNull(String[].class), isNull(String.class),
                isNull(String[].class), eq(DEFAULT_ORDER));
        verify(provider).provideReader(same(cursor));
        verify(action).call(mock1);
        verify(action).call(mock2);
        verifyNoMoreInteractions(action);
    }

    @Test
    public void shouldQueryAllWithExceptions() {
        // Given
        when(cursor.getCount()).thenReturn(2);
        when(cursor.moveToNext()).thenReturn(true, true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenThrow(new RuntimeException());


        // When
        try {
            proxyQuerier.queryAll(contentResolver, action);
            fail("Should leak exception");
        } catch (RuntimeException ignore) {
        }

        // Then
        verify(contentResolver).query(eq(FAKE_URI), isNull(String[].class),
                isNull(String.class), isNull(String[].class), eq(DEFAULT_ORDER));
        verify(provider).provideReader(same(cursor));
        verifyZeroInteractions(action);
    }

    @Test
    public void shouldQuery() {
        // Given
        int id = 42;
        Object mock1 = mock(Object.class);
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenReturn(mock1, (Object) null);


        // When
        proxyQuerier.query(contentResolver, action, id);

        // Then
        verify(contentResolver).query(eq(FAKE_URI), isNull(String[].class), eq(SELECT_BY_ID),
                eq(new String[]{FAKE_ID_STR}), eq(DEFAULT_ORDER));
        verify(provider).provideReader(same(cursor));
        verify(action).call(mock1);
        verifyNoMoreInteractions(action);
    }

    @Test
    public void shouldQueryWithException() {
        // Given
        int projectId = 42;
        when(cursor.getCount()).thenReturn(1);
        when(cursor.moveToNext()).thenReturn(true, false);
        when(contentResolver.query(any(Uri.class), any(String[].class), anyString(), any(String[].class), anyString()))
                .thenReturn(cursor);
        when(reader.instantiateAndFill()).thenThrow(new RuntimeException());

        // When
        try {
            proxyQuerier.query(contentResolver, action, projectId);
            fail("Should leak exception");
        } catch (RuntimeException ignore) {
        }

        // Then
        verify(contentResolver).query(eq(FAKE_URI), isNull(String[].class),
                eq(SELECT_BY_ID), eq(new String[]{FAKE_ID_STR}), eq(DEFAULT_ORDER));
        verify(provider).provideReader(same(cursor));
        verifyZeroInteractions(action);
    }

    @Test
    public void shouldSaveNew() {
        // Given
        Uri projectUri = mock(Uri.class);
        Object item = mock(Object.class);
        when(contentQuerier.getId(any())).thenReturn(-1);
        when(contentResolver.insert(any(Uri.class), any(ContentValues.class)))
                .thenReturn(projectUri);
        when(writer.toContentValues(any()))
                .thenReturn(contentValues);


        // When
        boolean success = proxyQuerier.save(contentResolver, item);

        // Then
        assertThat(success).isTrue();
        verify(contentResolver).insert(eq(FAKE_URI), same(contentValues));
    }

    @Test
    public void shouldSaveNewFail() {
        // Given
        Object item = mock(Object.class);
        when(contentQuerier.getId(any())).thenReturn(-1);
        when(contentResolver.insert(any(Uri.class), any(ContentValues.class)))
                .thenReturn(null);
        when(writer.toContentValues(any()))
                .thenReturn(contentValues);


        // When
        boolean success = proxyQuerier.save(contentResolver, item);

        // Then
        assertThat(success).isFalse();
        verify(contentResolver).insert(eq(FAKE_URI), same(contentValues));
    }

    @Test
    public void shouldSaveExisting() {
        // Given
        Object item = mock(Object.class);
        when(contentResolver.update(any(Uri.class), any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(1);
        when(writer.toContentValues(any()))
                .thenReturn(contentValues);


        // When
        boolean success = proxyQuerier.save(contentResolver, item);

        // Then
        assertThat(success).isTrue();
        verify(contentResolver).update(eq(FAKE_URI), same(contentValues), eq(SELECT_BY_ID), eq(new String[]{FAKE_ID_STR}));
    }

    @Test
    public void shouldSaveExistingFail() {
        // Given
        Object item = mock(Object.class);
        when(contentResolver.update(any(Uri.class), any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(0);
        when(writer.toContentValues(any()))
                .thenReturn(contentValues);


        // When
        boolean success = proxyQuerier.save(contentResolver, item);

        // Then
        assertThat(success).isFalse();
        verify(contentResolver).update(eq(FAKE_URI), same(contentValues), eq(SELECT_BY_ID), eq(new String[]{FAKE_ID_STR}));
    }

    @Test
    public void shouldDelete() {
        // Given
        Object project = mock(Object.class);
        when(contentResolver.delete(any(Uri.class), anyString(), any(String[].class)))
                .thenReturn(1);

        // When
        boolean success = proxyQuerier.delete(contentResolver, project);

        // Then
        assertThat(success).isTrue();
        verify(contentResolver).delete(eq(FAKE_URI), eq(SELECT_BY_ID), eq(new String[]{FAKE_ID_STR}));
    }

    @Test
    public void shouldDeleteExistingFail() {
        // Given
        Object project = mock(Object.class);
        when(contentResolver.delete(any(Uri.class), anyString(), any(String[].class)))
                .thenReturn(0);

        // When
        boolean success = proxyQuerier.delete(contentResolver, project);

        // Then
        assertThat(success).isFalse();
        verify(contentResolver).delete(eq(FAKE_URI), eq(SELECT_BY_ID), eq(new String[]{FAKE_ID_STR}));
    }


    static class ProxyContentQuerier extends BaseContentQuerier<Object> {

        private final BaseContentQuerier<Object> delegate;

        ProxyContentQuerier(BaseContentQuerier<Object> delegate, IOProvider<Object> ioProvider) {
            super(ioProvider);
            this.delegate = delegate;
        }

        @NonNull @Override protected Uri getUri() {
            return delegate.getUri();
        }

        @Override protected String selectById() {
            return delegate.selectById();
        }

        @Override protected int getId(@NonNull Object item) {
            return delegate.getId(item);
        }

        @Override protected String defaultOrder() {
            return delegate.defaultOrder();
        }
    }
}