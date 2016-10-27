package fr.xgouchet.chronorg.data.queriers;

import android.content.ContentResolver;
import android.support.annotation.NonNull;

import rx.functions.Action1;

/**
 * @author Xavier Gouchet
 */
public interface  ContentQuerier<T> {

    void queryAll(@NonNull ContentResolver contentResolver, @NonNull Action1<T> action);

    void query(@NonNull ContentResolver contentResolver, @NonNull Action1<T> action, int id);

    boolean save(@NonNull ContentResolver contentResolver, @NonNull T item);

    boolean delete(@NonNull ContentResolver contentResolver, @NonNull T item);
}
