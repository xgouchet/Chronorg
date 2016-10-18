package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.ui.fragments.EventEditFragment;
import fr.xgouchet.chronorg.ui.presenters.EventEditPresenter;

/**
 * @author Xavier Gouchet
 */
public class EventEditActivity extends BaseActivity {

    public static final String EXTRA_PROJECT_ID = "project_id";
    public static final String EXTRA_EVENT = "event";

    public static Intent intentNewEvent(@NonNull Context context, int entityId) {
        Intent intent = new Intent(context, EventEditActivity.class);
        intent.putExtra(EXTRA_PROJECT_ID, entityId);
        return intent;
    }

    public static Intent intentEditEvent(@NonNull Context context, @NonNull Event event) {
        Intent intent = new Intent(context, EventEditActivity.class);
        intent.putExtra(EXTRA_EVENT, event);
        return intent;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_edit);
        EventEditFragment fragment = (EventEditFragment) getSupportFragmentManager().findFragmentById(R.id.event_edit_fragment);


        Intent intent = getIntent();
        Event event = null;
        if (intent.hasExtra(EXTRA_PROJECT_ID)) {
            int projectId = intent.getIntExtra(EXTRA_PROJECT_ID, -1);
            if (projectId <= 0) {
                Toast.makeText(this, "Bad entity id", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            event = new Event();
            event.setProjectId(projectId);
        } else if (intent.hasExtra(EXTRA_EVENT)) {
            event = intent.getParcelableExtra(EXTRA_EVENT);
        }

        if (event == null) {
            Toast.makeText(this, "Null entity", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        EventEditPresenter presenter = getActivityComponent().getEventEditPresenter();
        presenter.setEvent(event);
        presenter.setView(fragment);

    }
}
