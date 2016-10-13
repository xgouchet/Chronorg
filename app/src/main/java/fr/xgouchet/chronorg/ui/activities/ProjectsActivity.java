package fr.xgouchet.chronorg.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.ui.fragments.ProjectListFragment;
import fr.xgouchet.chronorg.ui.presenters.ProjectListPresenter;

/**
 * @author Xavier Gouchet
 */
public class ProjectsActivity extends BaseActivity {


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        ProjectListFragment fragment = (ProjectListFragment) getSupportFragmentManager().findFragmentById(R.id.projects_fragment);

        ProjectListPresenter projectListPresenter = getActivityComponent().getProjectListPresenter();
        projectListPresenter.setView(fragment);
    }
}
