package fr.xgouchet.chronorg.provider.queriers;

import android.content.ContentResolver;
import android.support.annotation.NonNull;

import rx.Subscriber;

/**
 * @author Xavier Gouchet
 */
public interface BaseContentQuerier<T> {

    void queryAll(@NonNull ContentResolver contentResolver, @NonNull Subscriber<? super T> subscriber);

    void query(@NonNull ContentResolver contentResolver, @NonNull Subscriber<? super T> subscriber, int id);

    boolean save(@NonNull ContentResolver contentResolver, @NonNull T item);

    boolean delete(@NonNull ContentResolver contentResolver, @NonNull T item);
}
