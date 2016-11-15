package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.fragments.ProjectEditFragment;
import fr.xgouchet.chronorg.ui.presenters.ProjectEditPresenter;

/**
 * @author Xavier Gouchet
 */
public class ProjectEditActivity extends BaseFragmentActivity<Project, ProjectEditFragment> {

    private static final String EXTRA_PROJECT = "project";

    public static Intent intentNewProject(@NonNull Context context) {
        Intent intent = new Intent(context, ProjectEditActivity.class);
        return intent;
    }

    public static Intent intentEditProject(@NonNull Context context, @NonNull Project project) {
        Intent intent = new Intent(context, ProjectEditActivity.class);
        intent.putExtra(EXTRA_PROJECT, project);
        return intent;
    }


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Setup BasePresenter
        ProjectEditPresenter presenter = getActivityComponent().getProjectEditPresenter();
        presenter.setProject(item);
        presenter.setView(fragment);
    }

    @NonNull @Override protected Project readItem(@Nullable Intent intent) {
        Project project;
        if ((intent != null) && intent.hasExtra(EXTRA_PROJECT)) {
            project = getIntent().getParcelableExtra(EXTRA_PROJECT);
        } else {
            project = new Project();
        }
        return project;
    }

    @NonNull @Override protected ProjectEditFragment createFragment() {
        return new ProjectEditFragment();
    }
}
