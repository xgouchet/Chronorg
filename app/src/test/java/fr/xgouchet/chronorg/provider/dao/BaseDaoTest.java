package fr.xgouchet.chronorg.provider.dao;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.fail;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyString;
import static org.mockito.Matchers.eq;
import static org.mockito.Matchers.isNull;
import static org.mockito.Matchers.same;
import static org.mockito.Mockito.inOrder;
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
public class BaseDaoTest {

    private static final String TABLE_NAME = "Foo";

    @Mock SQLiteOpenHelper openHelper;
    @Mock SQLiteDatabase writeableDatabase;
    @Mock SQLiteDatabase readableDatabase;

    private BaseDao dao;

    @Before
    public void setUp() {
        initMocks(this);

        when(openHelper.getReadableDatabase()).thenReturn(readableDatabase);
        when(openHelper.getWritableDatabase()).thenReturn(writeableDatabase);

        dao = new BaseDao(openHelper, TABLE_NAME);

    }


    @Test
    public void shouldInsert() {
        // Given
        ContentValues cv = mock(ContentValues.class);
        when(writeableDatabase.insert(anyString(), anyString(), any(ContentValues.class)))
                .thenReturn(42L);

        // When
        long result = dao.insert(cv);

        // Then
        assertThat(result).isEqualTo(42L);
        verifyZeroInteractions(readableDatabase);
        InOrder inOrder = inOrder(writeableDatabase);
        inOrder.verify(writeableDatabase).beginTransaction();
        inOrder.verify(writeableDatabase).insert(eq(TABLE_NAME), isNull(String.class), same(cv));
        inOrder.verify(writeableDatabase).setTransactionSuccessful();
        inOrder.verify(writeableDatabase).endTransaction();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void shouldInsertWithException() {
        // Given
        ContentValues cv = mock(ContentValues.class);
        when(writeableDatabase.insert(anyString(), anyString(), any(ContentValues.class)))
                .thenThrow(new SQLiteException());

        // When
        try {
            dao.insert(cv);
            fail("Should let exception leak");
        } catch (SQLiteException ignored) {
        }

        // Then
        verifyZeroInteractions(readableDatabase);
        InOrder inOrder = inOrder(writeableDatabase);
        inOrder.verify(writeableDatabase).beginTransaction();
        inOrder.verify(writeableDatabase).insert(eq(TABLE_NAME), isNull(String.class), same(cv));
        inOrder.verify(writeableDatabase).endTransaction();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void shouldUpdate() {
        // Given
        String selection = "foo";
        String[] selectionArgs = new String[]{"bar"};
        ContentValues cv = mock(ContentValues.class);
        when(writeableDatabase.update(anyString(), any(ContentValues.class), anyString(), any(String[].class)))
                .thenReturn(13);

        // When
        int result = dao.update(cv, selection, selectionArgs);

        // Then
        assertThat(result).isEqualTo(13);
        verifyZeroInteractions(readableDatabase);
        InOrder inOrder = inOrder(writeableDatabase);
        inOrder.verify(writeableDatabase).beginTransaction();
        inOrder.verify(writeableDatabase).update(eq(TABLE_NAME), same(cv), eq(selection), same(selectionArgs));
        inOrder.verify(writeableDatabase).setTransactionSuccessful();
        inOrder.verify(writeableDatabase).endTransaction();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void shouldUpdateWithException() {
        // Given
        String selection = "foo";
        String[] selectionArgs = new String[]{"bar"};
        ContentValues cv = mock(ContentValues.class);
        when(writeableDatabase.update(anyString(), any(ContentValues.class), anyString(), any(String[].class)))
                .thenThrow(new SQLiteException());

        // When
        try {
            dao.update(cv, selection, selectionArgs);
            fail("Should let exception leak");
        } catch (SQLiteException ignored) {
        }

        // Then
        verifyZeroInteractions(readableDatabase);
        InOrder inOrder = inOrder(writeableDatabase);
        inOrder.verify(writeableDatabase).beginTransaction();
        inOrder.verify(writeableDatabase).update(eq(TABLE_NAME), same(cv), eq(selection), same(selectionArgs));
        inOrder.verify(writeableDatabase).endTransaction();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void shouldDelete() {
        // Given
        String selection = "foo";
        String[] selectionArgs = new String[]{"bar"};
        when(writeableDatabase.delete(anyString(), anyString(), any(String[].class)))
                .thenReturn(7);

        // When
        int result = dao.delete(selection, selectionArgs);

        // Then
        assertThat(result).isEqualTo(7);
        verifyZeroInteractions(readableDatabase);
        InOrder inOrder = inOrder(writeableDatabase);
        inOrder.verify(writeableDatabase).beginTransaction();
        inOrder.verify(writeableDatabase).delete(eq(TABLE_NAME), eq(selection), same(selectionArgs));
        inOrder.verify(writeableDatabase).setTransactionSuccessful();
        inOrder.verify(writeableDatabase).endTransaction();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void shouldDeleteWithException() {
        // Given
        String selection = "foo";
        String[] selectionArgs = new String[]{"bar"};
        when(writeableDatabase.delete(anyString(), anyString(), any(String[].class)))
                .thenThrow(new SQLiteException());

        // When
        try {
            dao.delete(selection, selectionArgs);
            fail("Should let exception leak");
        } catch (SQLiteException ignored) {
        }

        // Then
        verifyZeroInteractions(readableDatabase);
        InOrder inOrder = inOrder(writeableDatabase);
        inOrder.verify(writeableDatabase).beginTransaction();
        inOrder.verify(writeableDatabase).delete(eq(TABLE_NAME), eq(selection), same(selectionArgs));
        inOrder.verify(writeableDatabase).endTransaction();
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void shouldQuery() {
        // Given
        String selection = "foo";
        String[] selectionArgs = new String[]{"bar"};
        String[] projection = new String[]{"eggs", "bacon"};
        String sort = "spam";
        Cursor cursor = mock(Cursor.class);
        when(readableDatabase.query(anyString(), any(String[].class), anyString(),
                any(String[].class), anyString(), anyString(), anyString()))
                .thenReturn(cursor);

        // When
        Cursor result = dao.query(projection, selection, selectionArgs, sort);

        // Then
        assertThat(result);
        verifyZeroInteractions(writeableDatabase);
        verify(readableDatabase).query(eq(TABLE_NAME), eq(projection), eq(selection),
                same(selectionArgs), isNull(String.class), isNull(String.class), eq(sort));
        verifyNoMoreInteractions(readableDatabase);
    }

    @Test
    public void shouldQueryWithException() {
        // Given
        String selection = "foo";
        String[] selectionArgs = new String[]{"bar"};
        String[] projection = new String[]{"eggs", "bacon"};
        String sort = "spam";
        Cursor cursor = mock(Cursor.class);
        when(readableDatabase.query(anyString(), any(String[].class), anyString(),
                any(String[].class), anyString(), anyString(), anyString()))
                .thenThrow(new SQLiteException());

        // When
        try {
            dao.query(projection, selection, selectionArgs, sort);
            fail("Should let exception leak");
        } catch (SQLiteException ignored) {
        }

        // Then
        verifyZeroInteractions(writeableDatabase);
        verify(readableDatabase).query(eq(TABLE_NAME), eq(projection), eq(selection),
                same(selectionArgs), isNull(String.class), isNull(String.class), eq(sort));
        verifyNoMoreInteractions(readableDatabase);
    }
}
