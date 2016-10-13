package fr.xgouchet.chronorg;

import android.app.Application;
import android.content.Context;
import android.support.annotation.NonNull;

import net.danlew.android.joda.JodaTimeAndroid;

import fr.xgouchet.chronorg.inject.components.ChronorgComponent;
import fr.xgouchet.chronorg.inject.components.DaggerChronorgComponent;
import fr.xgouchet.chronorg.inject.modules.GlobalModule;
import fr.xgouchet.chronorg.inject.modules.RepositoryModule;

/**
 * @author Xavier Gouchet
 */
public class BaseApplication extends Application {

    private ChronorgComponent chronorgComponent;

    public static BaseApplication from(@NonNull Context context) {
        return (BaseApplication) context.getApplicationContext();
    }

    @Override public void onCreate() {
        super.onCreate();

        chronorgComponent = DaggerChronorgComponent.builder()
                .globalModule(new GlobalModule(this))
                .repositoryModule(new RepositoryModule())
                .build();

        JodaTimeAndroid.init(this);
    }

    public ChronorgComponent getChronorgComponent() {
        return chronorgComponent;
    }
}
