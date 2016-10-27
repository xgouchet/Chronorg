package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.models.Event;
import fr.xgouchet.chronorg.ui.fragments.EventEditFragment;
import fr.xgouchet.chronorg.ui.presenters.EventEditPresenter;

/**
 * @author Xavier Gouchet
 */
public class EventEditActivity extends BaseFragmentActivity<Event, EventEditFragment> {

    public static final String EXTRA_PROJECT_ID = "project_id";
    public static final String EXTRA_EVENT = "event";

    public static Intent intentNewEvent(@NonNull Context context, int projectId) {
        Intent intent = new Intent(context, EventEditActivity.class);
        intent.putExtra(EXTRA_PROJECT_ID, projectId);
        return intent;
    }

    public static Intent intentEditEvent(@NonNull Context context, @NonNull Event event) {
        Intent intent = new Intent(context, EventEditActivity.class);
        intent.putExtra(EXTRA_EVENT, event);
        return intent;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EventEditPresenter presenter = getActivityComponent().getEventEditPresenter();
        presenter.setEvent(item);
        presenter.setView(fragment);

    }

    @NonNull @Override protected Event readItem(@Nullable Intent intent) {
        Event event;
        if ((intent != null) && intent.hasExtra(EXTRA_PROJECT_ID)) {
            int projectId = intent.getIntExtra(EXTRA_PROJECT_ID, -1);
            if (projectId <= 0) {
                throw new IllegalArgumentException("Invalid project id " + projectId);
            }
            event = new Event();
            event.setProjectId(projectId);
        } else if ((intent != null) && intent.hasExtra(EXTRA_EVENT)) {
            event = intent.getParcelableExtra(EXTRA_EVENT);
        } else {
            throw new IllegalArgumentException("No arguments for jump");
        }

        return event;
    }

    @NonNull @Override protected EventEditFragment createFragment() {
        return new EventEditFragment();
    }
}
