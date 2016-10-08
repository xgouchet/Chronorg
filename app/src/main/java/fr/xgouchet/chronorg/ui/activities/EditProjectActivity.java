package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.repositories.ProjectRepository;
import fr.xgouchet.chronorg.provider.ioproviders.ProjectIOProvider;
import fr.xgouchet.chronorg.ui.fragments.EditProjectFragment;
import fr.xgouchet.chronorg.ui.presenters.EditProjectPresenter;

/**
 * @author Xavier Gouchet
 */
public class EditProjectActivity extends AppCompatActivity {

    private static final String EXTRA_PROJECT = "project";

    public static Intent intentNewProject(@NonNull Context context) {
        Intent intent = new Intent(context, EditProjectActivity.class);
        return intent;
    }

    public static Intent intentEditProject(@NonNull Context context, @NonNull Project project) {
        Intent intent = new Intent(context, EditProjectActivity.class);
        intent.putExtra(EXTRA_PROJECT, project);
        return intent;
    }


    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        EditProjectFragment fragment = (EditProjectFragment) getSupportFragmentManager().findFragmentById(R.id.edit_project_fragment);


        // Get project from intent
        Project project = getIntent().getParcelableExtra(EXTRA_PROJECT);
        if (project == null) {
            project = new Project();
        }

        //TODO inject
        ProjectRepository repository = new ProjectRepository(this, new ProjectIOProvider());
        EditProjectPresenter presenter = new EditProjectPresenter(repository, fragment, project);
    }
}
