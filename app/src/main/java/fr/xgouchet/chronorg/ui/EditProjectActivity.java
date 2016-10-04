package fr.xgouchet.chronorg.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.ProjectRepository;
import fr.xgouchet.chronorg.model.Project;
import fr.xgouchet.chronorg.provider.dao.ProjectIOProvider;
import fr.xgouchet.chronorg.ui.newproject.EditProjectFragment;
import fr.xgouchet.chronorg.ui.newproject.EditProjectPresenter;

/**
 * @author Xavier Gouchet
 */
public class EditProjectActivity extends AppCompatActivity {

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_project);
        EditProjectFragment fragment = (EditProjectFragment) getSupportFragmentManager().findFragmentById(R.id.edit_project_fragment);

        //TODO inject
        ProjectRepository repository = new ProjectRepository(this, new ProjectIOProvider());
        // TODO read project from intent extras / saved instance state
        EditProjectPresenter presenter = new EditProjectPresenter(repository, fragment, new Project());
    }
}
