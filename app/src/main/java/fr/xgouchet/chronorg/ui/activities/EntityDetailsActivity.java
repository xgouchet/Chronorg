package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.ui.contracts.EntityDetailsContract;
import fr.xgouchet.chronorg.ui.fragments.JumpListFragment;
import fr.xgouchet.chronorg.ui.presenters.EntityDetailsPresenter;
import fr.xgouchet.chronorg.ui.presenters.JumpListPresenter;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class EntityDetailsActivity extends BaseActivity implements EntityDetailsContract.View {

    private static final String EXTRA_ENTITY = "entity";
    private EntityDetailsContract.Presenter presenter;

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

        // Entity details

        EntityDetailsPresenter entityDetailsPresenter = getActivityComponent().getEntityDetailsPresenter();
        entityDetailsPresenter.setEntity(entity);
        entityDetailsPresenter.setView(this);

        // Jump List
        JumpListFragment fragment = (JumpListFragment) getSupportFragmentManager().findFragmentById(R.id.jumps_fragment);
        fragment.setEntityId(entity.getId());
        JumpListPresenter jumpListPresenter = getActivityComponent().getJumpListPresenter();
        jumpListPresenter.setEntity(entity);
        jumpListPresenter.setView(fragment);
    }

    @Override protected void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override protected void onPause() {
        super.onPause();
        presenter.unsubscribe();
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
        presenter.editEntity();
    }


    private void deleteEntity() {
        presenter.deleteEntity();
    }

    @Override public void setPresenter(@NonNull EntityDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void showEditEntityUi(@NonNull Entity entity) {
        Intent intent = EntityEditActivity.intentEditEntity(this, entity);
        startActivity(intent);
    }

    @Override public void entityDeleted() {
        finish();
    }

    @Override public void entityDeleteError(@NonNull Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override public void setError(@Nullable Throwable throwable) {
    }

    @Override public void setContent(@NonNull Entity entity) {
        setTitle(entity.getName());
    }
}
