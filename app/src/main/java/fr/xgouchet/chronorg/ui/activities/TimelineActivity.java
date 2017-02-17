package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.fragments.ShardListFragment;
import fr.xgouchet.chronorg.ui.presenters.TimelinePresenter;

/**
 * @author Xavier F. Gouchet
 */
public class TimelineActivity extends BaseFragmentActivity<Project, ShardListFragment> {

    private static final String EXTRA_PROJECT = "project";

    public static Intent intentProjectTimeline(@NonNull Context context, @NonNull Project project) {
        Intent intent = new Intent(context, ProjectEditActivity.class);
        intent.putExtra(EXTRA_PROJECT, project);
        return intent;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//        // Setup BasePresenter
//        TimelinePresenter presenter = getActivityComponent().getTimelinePresenter();
//        presenter.setProject(item);
//        presenter.setView(fragment);
    }

    @NonNull
    @Override
    protected Project readItem(@Nullable Intent intent) {
        if ((intent != null) && intent.hasExtra(EXTRA_PROJECT)) {
            return getIntent().getParcelableExtra(EXTRA_PROJECT);
        } else {
           throw new IllegalArgumentException("Intent must have a \"project\" extra");
        }
    }

    @NonNull
    @Override
    protected ShardListFragment createFragment() {

        return null;
    }
}
