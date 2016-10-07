package fr.xgouchet.chronorg;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * @author Xavier Gouchet
 */
public class ChronorgApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();
        JodaTimeAndroid.init(this);
    }
}
