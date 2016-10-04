package fr.xgouchet.chronorg;

import android.app.Application;

import com.facebook.stetho.Stetho;

/**
 * @author Xavier Gouchet
 */
public class DebugChronorgApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
    }
}
