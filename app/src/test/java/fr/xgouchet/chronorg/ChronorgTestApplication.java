package fr.xgouchet.chronorg;

import android.app.Application;

import net.danlew.android.joda.JodaTimeAndroid;

/**
 * @author Xavier Gouchet
 */
public class ChronorgTestApplication extends Application {

    @Override public void onCreate() {
        super.onCreate();

        JodaTimeAndroid.init(this);
    }
}
