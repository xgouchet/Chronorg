package fr.xgouchet.chronorg.ui.viewholders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * @author Xavier Gouchet
 */
public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {

    protected BaseViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindItem(@NonNull T item);
}
