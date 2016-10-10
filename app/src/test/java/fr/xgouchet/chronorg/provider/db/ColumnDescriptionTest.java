package fr.xgouchet.chronorg.provider.db;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class ColumnDescriptionTest {

    // TODO check in SQLite specs what TYPE / OPTIONS are allowed
    // For instance can a blob or null be primary key ?
    // Can a null be Not Null ?

    private static final String TEST_NAME = "foo";


    @Test
    public void shouldConstructColumnWithoutOption() {
        ColumnDescription column = new ColumnDescription(TEST_NAME, ColumnDescription.TYPE_INTEGER);

        assertThat(column).isNotNull();
        assertThat(column.getName()).isEqualTo(TEST_NAME);
        assertThat(column.getType()).isEqualTo(ColumnDescription.TYPE_INTEGER);
        assertThat(column.isPrimaryKey()).isFalse();
        assertThat(column.isAutoIncrement()).isFalse();
        assertThat(column.isNotNull()).isFalse();
        assertThat(column.isUnique()).isFalse();
        assertThat(column.getSince()).isZero();

        assertThat(column.getDefinition())
                .isEqualTo(TEST_NAME + " INTEGER");
    }

    @Test
    public void shouldConstructColumnWithNullOption() {
        ColumnDescription column = new ColumnDescription(TEST_NAME, ColumnDescription.TYPE_TEXT, (String) null);

        assertThat(column).isNotNull();
        assertThat(column.getName()).isEqualTo(TEST_NAME);
        assertThat(column.getType()).isEqualTo(ColumnDescription.TYPE_TEXT);
        assertThat(column.isPrimaryKey()).isFalse();
        assertThat(column.isAutoIncrement()).isFalse();
        assertThat(column.isNotNull()).isFalse();
        assertThat(column.isUnique()).isFalse();
        assertThat(column.getSince()).isZero();

        assertThat(column.getDefinition())
                .isEqualTo(TEST_NAME + " TEXT");
    }

    @Test
    public void shouldConstructColumnWithNullArrayOption() {
        ColumnDescription column = new ColumnDescription(TEST_NAME, ColumnDescription.TYPE_BLOB, (String[]) null);

        assertThat(column).isNotNull();
        assertThat(column.getName()).isEqualTo(TEST_NAME);
        assertThat(column.getType()).isEqualTo(ColumnDescription.TYPE_BLOB);
        assertThat(column.isPrimaryKey()).isFalse();
        assertThat(column.isAutoIncrement()).isFalse();
        assertThat(column.isNotNull()).isFalse();
        assertThat(column.isUnique()).isFalse();
        assertThat(column.getSince()).isZero();

        assertThat(column.getDefinition())
                .isEqualTo(TEST_NAME + " BLOB");
    }


    @Test
    public void shouldAllowAutoIncWithInteger() {
        ColumnDescription column = new ColumnDescription(TEST_NAME, ColumnDescription.TYPE_INTEGER, ColumnDescription.AUTOINCREMENT);

        assertThat(column).isNotNull();
        assertThat(column.getName()).isEqualTo(TEST_NAME);
        assertThat(column.getType()).isEqualTo(ColumnDescription.TYPE_INTEGER);
        assertThat(column.isAutoIncrement()).isTrue();
        assertThat(column.isPrimaryKey()).isFalse();
        assertThat(column.isNotNull()).isFalse();
        assertThat(column.isUnique()).isFalse();
        assertThat(column.getSince()).isZero();

        assertThat(column.getDefinition())
                .isEqualTo(TEST_NAME + " INTEGER AUTOINCREMENT");
    }

    @Test
    public void shouldNotAllowAutoIncWithNull() {
        ColumnDescription column = new ColumnDescription(TEST_NAME, ColumnDescription.TYPE_NULL, ColumnDescription.AUTOINCREMENT);

        assertThat(column).isNotNull();
        assertThat(column.getName()).isEqualTo(TEST_NAME);
        assertThat(column.getType()).isEqualTo(ColumnDescription.TYPE_NULL);
        assertThat(column.isAutoIncrement()).isFalse();
        assertThat(column.isPrimaryKey()).isFalse();
        assertThat(column.isNotNull()).isFalse();
        assertThat(column.isUnique()).isFalse();
        assertThat(column.getSince()).isZero();

        assertThat(column.getDefinition())
                .isEqualTo(TEST_NAME + " NULL");
    }

    @Test
    public void shouldNotAllowAutoIncWithText() {
        ColumnDescription column = new ColumnDescription(TEST_NAME, ColumnDescription.TYPE_TEXT, ColumnDescription.AUTOINCREMENT);

        assertThat(column).isNotNull();
        assertThat(column.getName()).isEqualTo(TEST_NAME);
        assertThat(column.getType()).isEqualTo(ColumnDescription.TYPE_TEXT);
        assertThat(column.isAutoIncrement()).isFalse();
        assertThat(column.isPrimaryKey()).isFalse();
        assertThat(column.isNotNull()).isFalse();
        assertThat(column.isUnique()).isFalse();
        assertThat(column.getSince()).isZero();

        assertThat(column.getDefinition())
                .isEqualTo(TEST_NAME + " TEXT");
    }

    @Test
    public void shouldNotAllowAutoIncWithBlob() {
        ColumnDescription column = new ColumnDescription(TEST_NAME, ColumnDescription.TYPE_BLOB, ColumnDescription.AUTOINCREMENT);

        assertThat(column).isNotNull();
        assertThat(column.getName()).isEqualTo(TEST_NAME);
        assertThat(column.getType()).isEqualTo(ColumnDescription.TYPE_BLOB);
        assertThat(column.isAutoIncrement()).isFalse();
        assertThat(column.isPrimaryKey()).isFalse();
        assertThat(column.isNotNull()).isFalse();
        assertThat(column.isUnique()).isFalse();
        assertThat(column.getSince()).isZero();

        assertThat(column.getDefinition())
                .isEqualTo(TEST_NAME + " BLOB");
    }

    @Test
    public void shouldNotAllowAutoIncWithReal() {
        ColumnDescription column = new ColumnDescription(TEST_NAME, ColumnDescription.TYPE_REAL, ColumnDescription.AUTOINCREMENT);

        assertThat(column).isNotNull();
        assertThat(column.getName()).isEqualTo(TEST_NAME);
        assertThat(column.getType()).isEqualTo(ColumnDescription.TYPE_REAL);
        assertThat(column.isAutoIncrement()).isFalse();
        assertThat(column.isPrimaryKey()).isFalse();
        assertThat(column.isNotNull()).isFalse();
        assertThat(column.isUnique()).isFalse();
        assertThat(column.getSince()).isZero();

        assertThat(column.getDefinition())
                .isEqualTo(TEST_NAME + " REAL");
    }

    @Test
    public void shouldReadOptions() {
        ColumnDescription column = new ColumnDescription(TEST_NAME, ColumnDescription.TYPE_TEXT,
                ColumnDescription.AUTOINCREMENT, ColumnDescription.NOT_NULL, ColumnDescription.PRIMARY_KEY, ColumnDescription.UNIQUE);

        assertThat(column).isNotNull();
        assertThat(column.getName()).isEqualTo(TEST_NAME);
        assertThat(column.getType()).isEqualTo(ColumnDescription.TYPE_TEXT);
        assertThat(column.isAutoIncrement()).isFalse();
        assertThat(column.isPrimaryKey()).isTrue();
        assertThat(column.isNotNull()).isTrue();
        assertThat(column.isUnique()).isTrue();
        assertThat(column.getSince()).isZero();

        assertThat(column.getDefinition())
                .isEqualTo(TEST_NAME + " TEXT PRIMARY KEY NOT NULL UNIQUE");
    }

    @Test
    public void shouldConstructWithSinceValue() {
        ColumnDescription column = new ColumnDescription(TEST_NAME, ColumnDescription.TYPE_REAL, 42);

        assertThat(column).isNotNull();
        assertThat(column.getName()).isEqualTo(TEST_NAME);
        assertThat(column.getType()).isEqualTo(ColumnDescription.TYPE_REAL);
        assertThat(column.isAutoIncrement()).isFalse();
        assertThat(column.isPrimaryKey()).isFalse();
        assertThat(column.isNotNull()).isFalse();
        assertThat(column.isUnique()).isFalse();
        assertThat(column.getSince()).isEqualTo(42);

        assertThat(column.getDefinition())
                .isEqualTo(TEST_NAME + " REAL");
    }

}