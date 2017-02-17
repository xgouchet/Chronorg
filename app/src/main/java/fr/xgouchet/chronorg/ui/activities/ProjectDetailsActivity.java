package fr.xgouchet.chronorg.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import butterknife.BindView;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.ui.adapters.PageFragmentAdapter;
import fr.xgouchet.chronorg.ui.contracts.ProjectDetailsContract;
import fr.xgouchet.chronorg.ui.presenters.ProjectDetailsPresenter;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class ProjectDetailsActivity
        extends BaseActivity
        implements ProjectDetailsContract.View {

    private static final String EXTRA_PROJECT = "project";

    @BindView(R.id.pager) ViewPager viewPager;
    private PageFragmentAdapter pageAdapter;
    private ProjectDetailsContract.Presenter presenter;

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
            Toast.makeText(this, R.string.error_project_details_empty, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        setTitle(project.getName());

        pageAdapter = new PageFragmentAdapter(getSupportFragmentManager(), this, project);
        viewPager.setAdapter(pageAdapter);

        ProjectDetailsPresenter projectDetailsPresenter = getActivityComponent().getProjectDetailsPresenter();
        projectDetailsPresenter.setProject(project);
        projectDetailsPresenter.setView(this);
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

        menu.findItem(R.id.timeline).setVisible(true);
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        boolean result = true;

        switch (item.getItemId()) {
            case R.id.timeline:
                showTimeline();
            case R.id.edit:
                editProject();
                break;
            case R.id.delete:
                deleteProject();
                break;
            default:
                result = super.onOptionsItemSelected(item);
                break;
        }
        return result;
    }

    private void showTimeline() {
        presenter.showTimeline();
    }

    private void editProject() {
        presenter.editProject();
    }

    private void deleteProject() {
        presenter.deleteProject();
    }

    @Override public void setPresenter(@NonNull ProjectDetailsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void showEditProjectUi(@NonNull Project project) {
        Intent intent = ProjectEditActivity.intentEditProject(this, project);
        startActivity(intent);
    }

    @Override
    public void showTimelineUi(@NonNull Project project) {
        Intent intent = TimelineActivity.intentProjectTimeline(this, project);
        startActivity(intent);
    }

    @Override public void projectDeleted() {
        finish();
    }

    @Override public void projectDeleteError(@NonNull Throwable e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
    }

    @Override public void setError(@Nullable Throwable throwable) {
    }

    @Override public void setContent(@NonNull Project project) {
        setTitle(project.getName());
    }
}
