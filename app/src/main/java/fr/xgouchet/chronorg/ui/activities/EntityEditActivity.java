package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.widget.Toast;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.fragments.EntityEditFragment;
import fr.xgouchet.chronorg.ui.presenters.EntityEditPresenter;

/**
 * @author Xavier Gouchet
 */
public class EntityEditActivity extends BaseActivity {

    public static final String EXTRA_PROJECT_ID = "project_id";
    public static final String EXTRA_ENTITY = "entity";

    public static Intent intentNewEntity(@NonNull Context context, int projectId) {
        Intent intent = new Intent(context, EntityEditActivity.class);
        intent.putExtra(EXTRA_PROJECT_ID, projectId);
        return intent;
    }

    public static Intent intentEditEntity(@NonNull Context context, @NonNull Entity entity) {
        Intent intent = new Intent(context, EntityEditActivity.class);
        intent.putExtra(EXTRA_ENTITY, entity);
        return intent;
    }

    @Override protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_entity);
        EntityEditFragment fragment = (EntityEditFragment) getSupportFragmentManager().findFragmentById(R.id.edit_entity_fragment);


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
        } else if (intent.hasExtra(EXTRA_ENTITY)) {
            entity = intent.getParcelableExtra(EXTRA_ENTITY);
        }

        if (entity == null) {
            Toast.makeText(this, "Null entity", Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        EntityEditPresenter presenter = getActivityComponent().getEntityEditPresenter();
        presenter.setEntity(entity);
        presenter.setView(fragment);

    }
}
