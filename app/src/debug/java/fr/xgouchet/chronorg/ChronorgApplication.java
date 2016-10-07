package fr.xgouchet.chronorg;

import android.app.Application;

import com.facebook.stetho.Stetho;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * @author Xavier Gouchet
 */
public class ChronorgApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        Stetho.initializeWithDefaults(this);
        JodaTimeAndroid.init(this);
    }
}
