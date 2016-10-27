package fr.xgouchet.chronorg.ui.fragments;

import android.content.Intent;
import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.ui.activities.JumpEditActivity;
import fr.xgouchet.chronorg.ui.adapters.JumpsAdapter;

/**
 * @author Xavier Gouchet
 */
public class JumpListFragment extends BaseListFragment<Jump, JumpsAdapter> {

    private int entityId = -1;


    @Override protected JumpsAdapter getAdapter() {
        return new JumpsAdapter(this);
    }

    public void setEntityId(int entityId) {
        this.entityId = entityId;
    }


    @Override public void showCreateItemUi() {
        Intent intent = JumpEditActivity.intentNewJump(getActivity(), entityId);
        getActivity().startActivity(intent);
    }


    @Override public void showItem(@NonNull Jump item) {
        Intent intent = JumpEditActivity.intentEditJump(getActivity(), item);
        getActivity().startActivity(intent);
    }
}
