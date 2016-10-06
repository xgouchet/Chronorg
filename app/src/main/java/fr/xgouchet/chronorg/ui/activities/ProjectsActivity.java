package fr.xgouchet.chronorg.ui.activities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.repositories.ProjectRepository;
import fr.xgouchet.chronorg.provider.ioproviders.ProjectIOProvider;
import fr.xgouchet.chronorg.ui.fragments.ProjectListFragment;
import fr.xgouchet.chronorg.ui.presenters.ProjectListPresenter;

/**
 * @author Xavier Gouchet
 */
public class ProjectsActivity extends AppCompatActivity {


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        ProjectListFragment fragment = (ProjectListFragment) getSupportFragmentManager().findFragmentById(R.id.projects_fragment);

        // TODO inject
        ProjectRepository repository = new ProjectRepository(this, new ProjectIOProvider());
        ProjectListPresenter projectListPresenter = new ProjectListPresenter(repository, fragment);

    }
}
