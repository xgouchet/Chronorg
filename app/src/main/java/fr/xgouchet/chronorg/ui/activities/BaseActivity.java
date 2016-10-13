package fr.xgouchet.chronorg.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import fr.xgouchet.chronorg.BaseApplication;
import fr.xgouchet.chronorg.inject.components.ActivityComponent;
import fr.xgouchet.chronorg.inject.components.ChronorgComponent;
import fr.xgouchet.chronorg.inject.components.DaggerActivityComponent;
import fr.xgouchet.chronorg.inject.modules.PresenterModule;

/**
 * @author Xavier Gouchet
 */
public class BaseActivity extends AppCompatActivity {

    private ChronorgComponent chronorgComponent;
    private ActivityComponent activityComponent;

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        chronorgComponent = BaseApplication.from(this).getChronorgComponent();
        activityComponent = DaggerActivityComponent.builder()
                .chronorgComponent(chronorgComponent)
                .presenterModule(new PresenterModule())
                .build();
    }

    public ChronorgComponent getChronorgComponent() {
        return chronorgComponent;
    }

    public ActivityComponent getActivityComponent() {
        return activityComponent;
    }
}
