package fr.xgouchet.chronorg.inject.modules;

import android.content.Context;
import android.support.annotation.NonNull;

import dagger.Module;
import dagger.Provides;
import fr.xgouchet.chronorg.inject.annotations.ApplicationContext;

/**
 * @author Xavier Gouchet
 */
@Module
public class GlobalModule {

    @NonNull private final Context appContext;

    public GlobalModule(@NonNull Context appContext) {
        this.appContext = appContext;
    }

    @Provides
    @ApplicationContext
    @NonNull
    public Context provideAppContext() {
        return appContext;
    }
}
