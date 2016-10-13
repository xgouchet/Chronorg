package fr.xgouchet.chronorg.ui.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;
import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.ui.adapters.JumpsAdapter;
import fr.xgouchet.chronorg.ui.contracts.JumpListContract;
import fr.xgouchet.chronorg.ui.viewholders.JumpViewHolder;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public class JumpListFragment extends Fragment
        implements JumpListContract.View, JumpViewHolder.Listener {

    @BindView(android.R.id.list) RecyclerView list;
    @BindView(R.id.loading) ProgressBar loading;
    @BindView(R.id.fab) FloatingActionButton fab;
    @BindView(R.id.message) TextView message;

    private JumpListContract.Presenter presenter;
    private final JumpsAdapter adapter;

    public JumpListFragment() {
        adapter = new JumpsAdapter(new ArrayList<Jump>(), this);
    }

    @Nullable @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_list, container, false);

        bind(this, view);

        list.setLayoutManager(new LinearLayoutManager(getActivity()));
        list.setAdapter(adapter);
        return view;
    }


    @Override public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override public void setPresenter(@NonNull JumpListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override public void setLoading(boolean active) {
        loading.setVisibility(active ? View.VISIBLE : View.GONE);
        fab.setVisibility(active ? View.GONE : View.VISIBLE);
    }

    @Override public void setEmpty() {
        message.setText(R.string.empty_jumps_list);
        message.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
    }

    @Override public void setError(@Nullable Throwable throwable) {
        message.setText(R.string.error_jumps_list);
        message.setVisibility(View.VISIBLE);
        list.setVisibility(View.GONE);
    }

    @Override public void setContent(@NonNull List<Jump> jumps) {
        adapter.update(jumps);
        message.setVisibility(View.GONE);
        list.setVisibility(View.VISIBLE);
    }

    @Override public void showCreateItemUi() {
        // TODO handle tablet
//        Intent intent = EditProjectActivity.intentNewProject(getActivity());
//        getActivity().startActivity(intent);
    }

    @OnClick(R.id.fab) public void onCreateNewJump() {
        presenter.createNewItem();
    }

    @Override public void showItem(@NonNull Jump item) {
//        Intent intent = ProjectDetailsActivity.buildIntent(getActivity(), item);
//
//        getActivity().startActivity(intent);
    }

    @Override public void onJumpSelected(@NonNull Jump jump) {
        presenter.itemSelected(jump);
    }
}
