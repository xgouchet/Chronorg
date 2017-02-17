package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.models.Timeline;
import fr.xgouchet.chronorg.ui.fragments.PortalEditFragment;
import fr.xgouchet.chronorg.ui.fragments.TimelineEditFragment;
import fr.xgouchet.chronorg.ui.presenters.TimelineEditPresenter;

/**
 * @author Xavier F. Gouchet
 */
public class TimelineEditActivity extends BaseFragmentActivity<Timeline, TimelineEditFragment> {
    public static final String EXTRA_PROJECT_ID = "project_id";
    private static final String EXTRA_TIMELINE = "timeline";

    public static Intent intentNewTimeline(@NonNull Context context, int projectId) {
        Intent intent = new Intent(context, TimelineEditActivity.class);
        intent.putExtra(EXTRA_PROJECT_ID, projectId);
        return intent;
    }

    public static Intent intentEditTimeline(@NonNull Context context, @NonNull Timeline ptimelinertal) {
        Intent intent = new Intent(context, TimelineEditActivity.class);
        intent.putExtra(EXTRA_TIMELINE, ptimelinertal);
        return intent;
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup BasePresenter
        TimelineEditPresenter presenter = getActivityComponent().getTimelineEditPresenter();
        presenter.setTimeline(item);
        presenter.setView(fragment);
    }

    @NonNull
    @Override
    protected Timeline readItem(@Nullable Intent intent) {
        Timeline timeline;


        if ((intent != null) && intent.hasExtra(EXTRA_PROJECT_ID)) {
            int projectId = intent.getIntExtra(EXTRA_PROJECT_ID, -1);
            if (projectId <= 0) {
                throw new IllegalArgumentException("Invalid project id " + projectId);
            }
            timeline = new Timeline();
            timeline.setProjectId(projectId);
        } else if ((intent != null) && intent.hasExtra(EXTRA_TIMELINE)) {
            timeline = intent.getParcelableExtra(EXTRA_TIMELINE);
        } else {
            throw new IllegalArgumentException("No arguments for timeline");
        }
        return timeline;
    }

    @NonNull
    @Override
    protected TimelineEditFragment createFragment() {
        return new TimelineEditFragment();
    }
}
