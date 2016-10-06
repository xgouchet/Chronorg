package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.data.repositories.ProjectRepository;
import fr.xgouchet.chronorg.provider.ioproviders.EntityIOProvider;
import fr.xgouchet.chronorg.provider.ioproviders.ProjectIOProvider;
import fr.xgouchet.chronorg.ui.fragments.ProjectDetailsFragment;
import fr.xgouchet.chronorg.ui.presenters.EntityListPresenter;
import fr.xgouchet.chronorg.ui.presenters.ProjectDetailsPresenter;

import static butterknife.ButterKnife.bind;

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
        bind(this);


        // Get project from intent
        Project project = getIntent().getParcelableExtra(EXTRA_PROJECT);
        if (project == null) {
            // TODO Toast
            finish();
            return;
        }

        // TODO inject presenters
        ProjectDetailsFragment projectDetailsFragment = (ProjectDetailsFragment) getSupportFragmentManager().findFragmentById(R.id.project_details_fragment);
        ProjectRepository projectRepository = new ProjectRepository(this, new ProjectIOProvider());
        ProjectDetailsPresenter projectDetailsPresenter =
                new ProjectDetailsPresenter(projectRepository,
                        projectDetailsFragment.getProjectDetailsView(),
                        project);

        // TODO inject presenters
        EntityRepository entityRepository = new EntityRepository(this, new EntityIOProvider());
        EntityListPresenter presenter =
                new EntityListPresenter(entityRepository,
                        projectDetailsFragment.getEntityListView(), project);
    }


}
