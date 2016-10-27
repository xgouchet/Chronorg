package fr.xgouchet.chronorg.data.formatters;

import android.support.annotation.NonNull;

/**
 * @author Xavier Gouchet
 */
public interface Formatter<T> {

    String format(@NonNull T item);
}
