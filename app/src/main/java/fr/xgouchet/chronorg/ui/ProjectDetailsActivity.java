package fr.xgouchet.chronorg.ui;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.ProjectRepository;
import fr.xgouchet.chronorg.model.Project;
import fr.xgouchet.chronorg.provider.dao.ProjectIOProvider;
import fr.xgouchet.chronorg.ui.projects.ProjectDetailsFragment;
import fr.xgouchet.chronorg.ui.projects.ProjectDetailsPresenter;

/**
 * @author Xavier Gouchet
 */
public class ProjectDetailsActivity extends AppCompatActivity {

    private static final String EXTRA_PROJECT = "project";

    public static Intent buildIntent(@NonNull Context context, @NonNull Project project) {
        Intent intent = new Intent(context, ProjectDetailsActivity.class);
        intent.putExtra(EXTRA_PROJECT, project);
        return intent;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_project_details);
        ProjectDetailsFragment fragment = (ProjectDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.projects_fragment);

        // Get project from intent
        Project project = getIntent().getParcelableExtra(EXTRA_PROJECT);
        if (project == null) {
            // TODO Toast
            finish();
            return;
        }

        // TODO inject presenters
        ProjectRepository projectRepository = new ProjectRepository(this, new ProjectIOProvider());
        ProjectDetailsPresenter presenter = new ProjectDetailsPresenter(projectRepository, project, fragment);
    }
}
