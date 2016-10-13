package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.fragments.JumpListFragment;
import fr.xgouchet.chronorg.ui.presenters.JumpListPresenter;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class EntityDetailsActivity extends BaseActivity {

    private static final String EXTRA_ENTITY = "entity";

    public static Intent buildIntent(@NonNull Context context, @NonNull Entity entity) {
        Intent intent = new Intent(context, EntityDetailsActivity.class);
        intent.putExtra(EXTRA_ENTITY, entity);
        return intent;
    }

    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entity_details);
        bind(this);

        Entity entity = getIntent().getParcelableExtra(EXTRA_ENTITY);
        if (entity == null) {
            Toast.makeText(this, R.string.error_entity_details_empty, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setTitle(entity.getName());

        JumpListFragment fragment = (JumpListFragment) getSupportFragmentManager().findFragmentById(R.id.jumps_fragment);

        JumpListPresenter jumpListPresenter = getActivityComponent().getJumpListPresenter();
        jumpListPresenter.setEntity(entity);
        jumpListPresenter.setView(fragment);
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.view, menu);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;

        switch (item.getItemId()) {
            case R.id.edit:
                editEntity();
                break;
            case R.id.delete:
                deleteEntity();
                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }

    private void editEntity() {
//   TODO     presenter.editProject();
    }

    private void deleteEntity() {
//   TODO     presenter.deleteProject();
    }
}
