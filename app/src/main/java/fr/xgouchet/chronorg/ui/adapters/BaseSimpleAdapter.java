package fr.xgouchet.chronorg.ui.adapters;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import fr.xgouchet.chronorg.ui.viewholders.BaseViewHolder;

/**
 * @author Xavier Gouchet
 */
public abstract class BaseSimpleAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH> {

    private final List<T> content = new ArrayList<>();

    @Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayout(viewType), parent, false);

        return instantiateViewHolder(viewType, view);
    }

    @Override public void onBindViewHolder(VH holder, int position) {
        T item = content.get(position);
        holder.bindItem(item);
    }

    @Override public final int getItemCount() {
        return content.size();
    }

    final public void update(List<T> newContent) {
        // TODO use DiffUtils
        content.clear();
        content.addAll(newContent);
        notifyDataSetChanged();
    }

    protected abstract VH instantiateViewHolder(int viewType, View view);


    @LayoutRes
    protected abstract int getLayout(int viewType);

}
