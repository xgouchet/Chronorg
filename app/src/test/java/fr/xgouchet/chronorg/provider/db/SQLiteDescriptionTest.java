package fr.xgouchet.chronorg.provider.db;

import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class SQLiteDescriptionTest {

    private static final String TEST_NAME = "FOO";
    private static final int TEST_VERSION = 42;

    private static final String TEST_TABLE_NAME_0 = "BAR";
    private static final String TEST_TABLE_NAME_1 = "BAZ";
    private static final String TEST_TABLE_NAME_2 = "BAM";
    private static final String TEST_TABLE_NAME_3 = "BAL";


    private static final String TEST_TABLE_CREATE_0 = "CREATE TABLE BAR";
    private static final String TEST_TABLE_CREATE_1 = "CREATE TABLE BAZ";
    private static final String TEST_TABLE_UPGRADE_0 = "ALTER TABLE BAR ADD COLUMN SPAM TEXT";
    private static final String TEST_TABLE_DROP_2 = "DROP TABLE BAM";

    private SQLiteDescription description;

    @Before
    public void setUp() {
        description = new SQLiteDescription(TEST_NAME, TEST_VERSION);
    }

    @After
    public void tearDown() {
        description = null;
    }

    @Test
    public void shouldGetName() {
        assertThat(description.getName()).isEqualTo(TEST_NAME);
    }

    @Test
    public void shouldGetVersion() {
        assertThat(description.getVersion()).isEqualTo(TEST_VERSION);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddTwoTablesWithSameName() {
        TableDescription table0 = mock(TableDescription.class);
        when(table0.getName()).thenReturn(TEST_TABLE_NAME_0);

        TableDescription table1 = mock(TableDescription.class);
        when(table1.getName()).thenReturn(TEST_TABLE_NAME_0);

        try {
            description.addTable(table0);
        } catch (IllegalArgumentException e) {
            fail("Should be able to add a table", e);
        }

        description.addTable(table1);
    }


    @Test
    public void shouldIgnoreWhenAddingSameTableTwice() {
        TableDescription table0 = mock(TableDescription.class);
        when(table0.getName()).thenReturn(TEST_TABLE_NAME_0);

        description.addTable(table0);
        description.addTable(table0);
    }

    @Test
    public void shouldCreateDatabase() {
        TableDescription table0 = mock(TableDescription.class);
        when(table0.getName()).thenReturn(TEST_TABLE_NAME_0);
        when(table0.getSince()).thenReturn(0);
        when(table0.getUntil()).thenReturn(Integer.MAX_VALUE);
        when(table0.getCreateStatement(anyInt())).thenReturn(TEST_TABLE_CREATE_0);

        TableDescription table1 = mock(TableDescription.class);
        when(table1.getName()).thenReturn(TEST_TABLE_NAME_1);
        when(table1.getSince()).thenReturn(0);
        when(table1.getUntil()).thenReturn(Integer.MAX_VALUE);
        when(table1.getCreateStatement(anyInt())).thenReturn(TEST_TABLE_CREATE_1);

        SQLiteDatabase db = mock(SQLiteDatabase.class);
        doNothing().when(db).execSQL(anyString());
        doNothing().when(db).beginTransaction();
        doNothing().when(db).setTransactionSuccessful();
        doNothing().when(db).endTransaction();

        description.addTable(table0);
        description.addTable(table1);
        description.createDatabase(db);

        // verify
        verify(table0).getCreateStatement(TEST_VERSION);
        verify(table1).getCreateStatement(TEST_VERSION);
        verify(db).beginTransaction();
        verify(db).execSQL(TEST_TABLE_CREATE_0);
        verify(db).execSQL(TEST_TABLE_CREATE_1);
        verify(db).setTransactionSuccessful();
        verify(db).endTransaction();
    }

    @Test(expected = SQLException.class)
    public void shouldEndTransactionEvenOnFail() {
        TableDescription table0 = mock(TableDescription.class);
        when(table0.getName()).thenReturn(TEST_TABLE_NAME_0);
        when(table0.getSince()).thenReturn(0);
        when(table0.getUntil()).thenReturn(Integer.MAX_VALUE);
        when(table0.getCreateStatement(anyInt())).thenReturn(TEST_TABLE_CREATE_0);

        SQLiteDatabase db = mock(SQLiteDatabase.class);
        doNothing().when(db).beginTransaction();
        doThrow(new SQLException()).when(db).execSQL(anyString());
        doNothing().when(db).endTransaction();

        // Test
        description.addTable(table0);
        description.createDatabase(db);

        // verify
        verify(table0).getCreateStatement(TEST_VERSION);
        verify(db).beginTransaction();
        verify(db).execSQL(TEST_TABLE_CREATE_0);
        verify(db).endTransaction();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void shouldNotDowngradeDatabase() {
        SQLiteDatabase db = mock(SQLiteDatabase.class);
        description.downgradeDatabase(db, 5, 4);
    }

    @Test
    public void shouldUpgradeDatabase() {
        // Given
        TableDescription table0 = mock(TableDescription.class);
        when(table0.getName()).thenReturn(TEST_TABLE_NAME_0);
        when(table0.getSince()).thenReturn(0);
        when(table0.getUntil()).thenReturn(Integer.MAX_VALUE);
        when(table0.getUpgradeStatement(anyInt(), anyInt())).thenReturn(TEST_TABLE_UPGRADE_0);

        TableDescription table1 = mock(TableDescription.class);
        when(table1.getName()).thenReturn(TEST_TABLE_NAME_1);
        when(table1.getSince()).thenReturn(TEST_VERSION);
        when(table1.getUntil()).thenReturn(Integer.MAX_VALUE);
        when(table1.getCreateStatement(anyInt())).thenReturn(TEST_TABLE_CREATE_1);

        TableDescription table2 = mock(TableDescription.class);
        when(table2.getName()).thenReturn(TEST_TABLE_NAME_2);
        when(table2.getSince()).thenReturn(0);
        when(table2.getUntil()).thenReturn(TEST_VERSION - 1);
        when(table2.getDropStatement()).thenReturn(TEST_TABLE_DROP_2);

        TableDescription table3 = mock(TableDescription.class);
        when(table3.getName()).thenReturn(TEST_TABLE_NAME_3);
        when(table3.getSince()).thenReturn(0);
        when(table3.getUntil()).thenReturn(TEST_VERSION);
        when(table3.getUpgradeStatement(anyInt(), anyInt())).thenReturn(null);

        SQLiteDatabase db = mock(SQLiteDatabase.class);

        // When
        description.addTable(table0);
        description.addTable(table1);
        description.addTable(table2);
        description.addTable(table3);
        description.upgradeDatabase(db, TEST_VERSION - 1, TEST_VERSION);

        // Then
        verify(db).execSQL(TEST_TABLE_UPGRADE_0);
        verify(db).execSQL(TEST_TABLE_CREATE_1);
        verify(db).execSQL(TEST_TABLE_DROP_2);
        verifyNoMoreInteractions(db);

    }

}