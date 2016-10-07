package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.provider.ioproviders.EntityIOProvider;
import fr.xgouchet.chronorg.ui.fragments.EditEntityFragment;
import fr.xgouchet.chronorg.ui.presenters.EditEntityPresenter;

/**
 * @author Xavier Gouchet
 */
public class EditEntityActivity extends AppCompatActivity {

    public static final String EXTRA_PROJECT_ID = "project_id";

    public static Intent intentNewEntity(@NonNull Context context, int projectId) {
        Intent intent = new Intent(context, EditEntityActivity.class);
        intent.putExtra(EXTRA_PROJECT_ID, projectId);
        return intent;
    }

    public static Intent intentEditEntity(@NonNull Context context, @NonNull Entity entity) {
        Intent intent = new Intent(context, EditEntityActivity.class);
//        intent.putExtra(EXTRA_PROJECT_ID, projectId);
        return intent;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entity);
        EditEntityFragment fragment = (EditEntityFragment) getSupportFragmentManager().findFragmentById(R.id.edit_entity_fragment);


        Intent intent = getIntent();
        Entity entity = null;
        if (intent.hasExtra(EXTRA_PROJECT_ID)) {
            int projectId = intent.getIntExtra(EXTRA_PROJECT_ID, -1);
            if (projectId <= 0) {
                Toast.makeText(this, "Bad project id", Toast.LENGTH_SHORT).show();
                finish();
                return;
            }
            entity = new Entity();
            entity.setProjectId(projectId);
        }

        if (entity == null) {
            Toast.makeText(this, "Null entity", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        //TODO inject
        EntityRepository repository = new EntityRepository(this, new EntityIOProvider());
        EditEntityPresenter presenter = new EditEntityPresenter(repository, fragment, entity);
    }
}
