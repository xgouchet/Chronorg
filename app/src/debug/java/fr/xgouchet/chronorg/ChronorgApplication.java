package fr.xgouchet.chronorg;

import com.facebook.stetho.Stetho;

/**
 * @author Xavier Gouchet
 */
public class ChronorgApplication extends BaseApplication {

    @Override public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
