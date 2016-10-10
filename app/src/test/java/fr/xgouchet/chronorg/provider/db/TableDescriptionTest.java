package fr.xgouchet.chronorg.provider.db;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class TableDescriptionTest {

    private static final String TEST_TABLE_NAME = "KAAMELOTT";
    private static final String TEST_COLUMN_NAME_0 = "foo";
    private static final String TEST_COLUMN_NAME_1 = "bar";
    private static final String TEST_COLUMN_NAME_2 = "baz";

    private static final String TEST_COLUMN_DESC_0 = "foo TEXT NOT NULL";
    private static final String TEST_COLUMN_DESC_1 = "bar INTEGER AUTOINCREMENT";
    private static final String TEST_COLUMN_DESC_2 = "baz BLOB";

    private TableDescription tableDescription;

    @Before
    public void setUp() throws Exception {
        tableDescription = new TableDescription(TEST_TABLE_NAME, 0, 13);
    }

    @Test
    public void should_buid_empty_table() {
        assertThat(tableDescription.getName()).isEqualTo(TEST_TABLE_NAME);
        assertThat(tableDescription.getSince()).isZero();
        assertThat(tableDescription.getUntil()).isEqualTo(13);
    }

    @Test
    public void should_buid_empty_table_with_default_values() {
        // When
        tableDescription = new TableDescription(TEST_TABLE_NAME);

        // Then
        assertThat(tableDescription.getName()).isEqualTo(TEST_TABLE_NAME);
        assertThat(tableDescription.getSince()).isEqualTo(0);
        assertThat(tableDescription.getUntil()).isEqualTo(Integer.MAX_VALUE);
    }


    @Test
    public void shouldBuildCreateStatementForEmptyTable() {
        assertThat(tableDescription.getCreateStatement(0))
                .isEqualTo("CREATE TABLE IF NOT EXISTS " + TEST_TABLE_NAME + " ()");
    }


    @Test
    public void shouldBuildTableCreateStatement() {
        ColumnDescription col0 = mock(ColumnDescription.class);
        when(col0.getName()).thenReturn(TEST_COLUMN_NAME_0);
        when(col0.getDefinition()).thenReturn(TEST_COLUMN_DESC_0);

        ColumnDescription col1 = mock(ColumnDescription.class);
        when(col1.getName()).thenReturn(TEST_COLUMN_NAME_1);
        when(col1.getDefinition()).thenReturn(TEST_COLUMN_DESC_1);

        tableDescription.addColumn(col0);
        tableDescription.addColumn(col1);

        assertThat(tableDescription.getCreateStatement(0))
                .isEqualTo("CREATE TABLE IF NOT EXISTS " + TEST_TABLE_NAME + " ("
                        + TEST_COLUMN_DESC_0 + "," + TEST_COLUMN_DESC_1 + ")");
    }

    @Test
    public void shouldBuildDropStatement() {
        assertThat(tableDescription.getDropStatement())
                .isEqualTo("DROP TABLE IF EXISTS " + TEST_TABLE_NAME);
    }

    @Test(expected = IllegalArgumentException.class)
    public void shouldNotAddTwoColumnsWithSameName() {
        ColumnDescription col0 = mock(ColumnDescription.class);
        when(col0.getName()).thenReturn(TEST_COLUMN_DESC_0);
        ColumnDescription col1 = mock(ColumnDescription.class);
        when(col1.getName()).thenReturn(TEST_COLUMN_DESC_0);

        try {
            tableDescription.addColumn(col0);
        } catch (IllegalArgumentException e) {
            fail("Should be able to add a column", e);
        }
        tableDescription.addColumn(col1);
    }


    @Test
    public void shouldIgnoreWhenAddingSameColumnsTwice() {
        ColumnDescription col0 = mock(ColumnDescription.class);
        when(col0.getName()).thenReturn(TEST_COLUMN_DESC_0);

        tableDescription.addColumn(col0);
        tableDescription.addColumn(col0);
    }

    @Test
    public void shouldUpgradeTable() {
        ColumnDescription col0 = mock(ColumnDescription.class);
        when(col0.getSince()).thenReturn(4);
        when(col0.getName()).thenReturn(TEST_COLUMN_NAME_0);
        when(col0.getDefinition()).thenReturn(TEST_COLUMN_DESC_0);
        ColumnDescription col1 = mock(ColumnDescription.class);
        when(col1.getSince()).thenReturn(0);
        when(col1.getName()).thenReturn(TEST_COLUMN_NAME_1);
        when(col1.getDefinition()).thenReturn(TEST_COLUMN_DESC_1);
        ColumnDescription col2 = mock(ColumnDescription.class);
        when(col2.getSince()).thenReturn(8);
        when(col2.getName()).thenReturn(TEST_COLUMN_NAME_2);
        when(col2.getDefinition()).thenReturn(TEST_COLUMN_DESC_2);

        tableDescription.addColumn(col0);
        tableDescription.addColumn(col1);
        tableDescription.addColumn(col2);

        String upgrade_1_2 = tableDescription.getUpgradeStatement(1, 2);
        String upgrade_3_4 = tableDescription.getUpgradeStatement(3, 4);
        String upgrade_4_5 = tableDescription.getUpgradeStatement(4, 5);

        assertThat(upgrade_1_2).isNullOrEmpty();
        assertThat(upgrade_3_4).isEqualTo("ALTER TABLE " + TEST_TABLE_NAME + " ADD COLUMN " + TEST_COLUMN_DESC_0 + ";");
        assertThat(upgrade_4_5).isNullOrEmpty();
    }
}