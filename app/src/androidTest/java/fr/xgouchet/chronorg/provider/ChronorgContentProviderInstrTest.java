package fr.xgouchet.chronorg.provider;

import android.support.test.InstrumentationRegistry;
import android.support.test.filters.LargeTest;
import android.support.test.runner.AndroidJUnit4;
import android.test.ProviderTestCase2;
import android.test.mock.MockContentResolver;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import fr.xgouchet.chronorg.provider.db.ChronorgSchema;

/**
 * @author Xavier Gouchet
 */
@RunWith(AndroidJUnit4.class)
@LargeTest
public class ChronorgContentProviderInstrTest extends ProviderTestCase2<ChronorgContentProvider>{

    public ChronorgContentProviderInstrTest() {
        super(ChronorgContentProvider.class, ChronorgSchema.AUTHORITY);
    }

    @Before
    @Override public void setUp() throws Exception {
        setContext(InstrumentationRegistry.getTargetContext());
        super.setUp();
    }

    @Test
    public void emptyQuery(){
        MockContentResolver contentResolver = getMockContentResolver();
        assertNotNull(contentResolver);
    }
}