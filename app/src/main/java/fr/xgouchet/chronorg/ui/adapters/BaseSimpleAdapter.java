package fr.xgouchet.chronorg.ui.adapters;

import android.support.annotation.LayoutRes;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

import fr.xgouchet.chronorg.ui.viewholders.BaseViewHolder;

/**
 * @author Xavier Gouchet
 */
public abstract class BaseSimpleAdapter<T, VH extends BaseViewHolder<T>> extends RecyclerView.Adapter<VH> {

    @Override public VH onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayout(viewType), parent, false);

        return instantiateViewHolder(viewType, view);
    }

    @Override public void onBindViewHolder(VH holder, int position) {
        T item = getItem(position);
        holder.bindItem(item);
    }

    public abstract void update(List<T> newContent);

    protected abstract VH instantiateViewHolder(int viewType, View view);


    @LayoutRes
    protected abstract int getLayout(int viewType);

    protected abstract T getItem(int position);
}
