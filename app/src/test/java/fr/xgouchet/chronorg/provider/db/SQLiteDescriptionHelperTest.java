package fr.xgouchet.chronorg.provider.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import fr.xgouchet.chronorg.BuildConfig;
import fr.xgouchet.chronorg.ChronorgTestApplication;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.anyInt;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * @author Xavier Gouchet
 */
@RunWith(RobolectricTestRunner.class)
@Config(constants = BuildConfig.class, sdk = 18, application = ChronorgTestApplication.class)
public class SQLiteDescriptionHelperTest {

    private static final String TEST_NAME = "FOO";
    private static final int TEST_VERSION = 42;

    @Mock SQLiteDescription description;
    @Mock SQLiteDescriptionProvider descriptionProvider;
    private SQLiteDescriptionHelper descriptionHelper;
    private Context context;

    @Before
    public void setUp() {
        initMocks(this);
        when(descriptionProvider.getDescription()).thenReturn(description);
        when(description.getVersion()).thenReturn(TEST_VERSION);
        when(description.getName()).thenReturn(TEST_NAME);
        context = mock(Context.class);

        descriptionHelper = new SQLiteDescriptionHelper(context, descriptionProvider, null);
    }

    @After
    public void tearDown() {
        description = null;
        descriptionHelper = null;
    }

    @Test
    public void shouldForwardCreation() {
        SQLiteDatabase db = mock(SQLiteDatabase.class);
        doNothing().when(description).createDatabase(any(SQLiteDatabase.class));

        descriptionHelper.onCreate(db);

        verify(description).createDatabase(db);
    }

    @Test
    public void shouldForwardUpgrade() {
        SQLiteDatabase db = mock(SQLiteDatabase.class);
        int oldVersion = 1;
        int newVersion = 42;
        doNothing().when(description).upgradeDatabase(any(SQLiteDatabase.class), anyInt(), anyInt());

        descriptionHelper.onUpgrade(db, oldVersion, newVersion);

        verify(description).upgradeDatabase(db, oldVersion, newVersion);
    }

    @Test
    public void shouldForwardDowngrade() {
        SQLiteDatabase db = mock(SQLiteDatabase.class);
        int oldVersion = 42;
        int newVersion = 1;
        doNothing().when(description).downgradeDatabase(any(SQLiteDatabase.class), anyInt(), anyInt());

        descriptionHelper.onDowngrade(db, oldVersion, newVersion);

        verify(description).downgradeDatabase(db, oldVersion, newVersion);
    }
}
