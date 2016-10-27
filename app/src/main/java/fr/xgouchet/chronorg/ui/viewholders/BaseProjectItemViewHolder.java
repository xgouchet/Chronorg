package fr.xgouchet.chronorg.ui.viewholders;

import android.support.annotation.ColorInt;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.OnClick;
import fr.xgouchet.chronorg.R;

import static butterknife.ButterKnife.bind;

/**
 * @author Xavier Gouchet
 */
public abstract class BaseProjectItemViewHolder<T> extends BaseViewHolder<T> {

    @BindView(R.id.name) TextView name;
    @BindView(R.id.underline) View underline;
    @BindView(R.id.info) TextView info;

    protected BaseProjectItemViewHolder(@Nullable Listener<T> listener, @NonNull View itemView) {
        super(listener, itemView);
        bind(this, itemView);
    }

    @Override protected void onBindItem(@NonNull T item) {
        name.setText(getName(item));

        underline.setBackgroundColor(getColor(item));

        final String info = getInfo(item);
        this.info.setText(info);
        this.info.setVisibility(TextUtils.isEmpty(info) ? View.GONE : View.VISIBLE);
    }

    @OnClick(R.id.item)
    public void onItemSelected() {
        fireSelected();
    }

    protected abstract String getName(@NonNull T item);

    @ColorInt protected abstract int getColor(@NonNull T item);

    protected abstract String getInfo(@NonNull T item);
}
