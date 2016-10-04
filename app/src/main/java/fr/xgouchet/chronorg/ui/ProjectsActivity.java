package fr.xgouchet.chronorg.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.ProjectRepository;
import fr.xgouchet.chronorg.provider.dao.ProjectIOProvider;
import fr.xgouchet.chronorg.ui.projects.ProjectsFragment;
import fr.xgouchet.chronorg.ui.projects.ProjectsPresenter;

/**
 * @author Xavier Gouchet
 */
public class ProjectsActivity extends AppCompatActivity {


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_projects);
        ProjectsFragment fragment = (ProjectsFragment) getSupportFragmentManager().findFragmentById(R.id.projects_fragment);

        // TODO inject
        ProjectRepository repository = new ProjectRepository(this, new ProjectIOProvider());
        ProjectsPresenter projectsPresenter = new ProjectsPresenter(repository, fragment);

    }
}
