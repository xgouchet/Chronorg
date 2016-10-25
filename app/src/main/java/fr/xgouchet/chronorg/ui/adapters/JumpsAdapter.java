package fr.xgouchet.chronorg.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.text.TextUtils;
import android.view.View;

import java.util.List;

import fr.xgouchet.chronorg.R;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.ui.viewholders.JumpViewHolder;

/**
 * @author Xavier Gouchet
 */
public class JumpsAdapter extends BaseSimpleAdapter<Jump, JumpViewHolder> {

    @NonNull /*package*/ final List<Jump> jumps;
    @NonNull private final JumpViewHolder.Listener listener;

    public JumpsAdapter(@NonNull List<Jump> entities, @NonNull JumpViewHolder.Listener listener) {
        this.jumps = entities;
        this.listener = listener;
    }


    @Override public int getItemCount() {
        return jumps.size();
    }

    public void update(@NonNull final List<Jump> newContent) {
        DiffUtil.DiffResult result = DiffUtil.calculateDiff(new DiffUtil.Callback() {
            @Override public int getOldListSize() {
                return jumps.size();
            }

            @Override public int getNewListSize() {
                return newContent.size();
            }

            @Override public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {
                return jumps.get(oldItemPosition).getId() == newContent.get(newItemPosition).getId();
            }

            @Override public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {
                return TextUtils.equals(
                        jumps.get(oldItemPosition).getName(),
                        newContent.get(newItemPosition).getName())
                        &&
                        // TODO compare values and not toString
                        TextUtils.equals(
                                jumps.get(oldItemPosition).getFrom().toString(),
                                newContent.get(newItemPosition).getFrom().toString())

                        &&
                        // TODO compare values and not toString
                        TextUtils.equals(
                                jumps.get(oldItemPosition).getTo().toString(),
                                newContent.get(newItemPosition).getTo().toString())
                        ;
            }
        });

        jumps.clear();
        jumps.addAll(newContent);
        result.dispatchUpdatesTo(this);
    }

    @Override protected JumpViewHolder instantiateViewHolder(int viewType, View view) {
        return new JumpViewHolder(listener, view);
    }

    @Override protected int getLayout(int viewType) {
        return R.layout.item_jump;
    }

    @Override protected Jump getItem(int position) {
        return jumps.get(position);
    }
}
