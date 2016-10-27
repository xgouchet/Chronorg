package fr.xgouchet.chronorg.ui.adapters;

import android.support.annotation.NonNull;
import android.view.View;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.ui.viewholders.BaseViewHolder;
import fr.xgouchet.chronorg.ui.viewholders.JumpViewHolder;

/**
 * @author Xavier Gouchet
 */
public class JumpsAdapter extends BaseSimpleAdapter<Jump, JumpViewHolder> {

    @NonNull private final BaseViewHolder.Listener<Jump> listener;

    public JumpsAdapter(@NonNull BaseViewHolder.Listener<Jump> listener) {
        this.listener = listener;
    }

    @Override protected JumpViewHolder instantiateViewHolder(int viewType, View view) {
        return new JumpViewHolder(listener, view);
    }

    @Override protected int getLayout(int viewType) {
        return R.layout.item_jump;
    }

}
