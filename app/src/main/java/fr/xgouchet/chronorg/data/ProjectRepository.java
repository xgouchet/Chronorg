package fr.xgouchet.chronorg.data;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.model.Project;
import fr.xgouchet.chronorg.provider.dao.BaseContentValuesWriter;
import fr.xgouchet.chronorg.provider.dao.BaseCursorReader;
import fr.xgouchet.chronorg.provider.dao.ProjectIOProvider;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.Observable;
import rx.Subscriber;

/**
 * @author Xavier Gouchet
 */
@Trace
public class ProjectRepository {

    @NonNull /*package*/ final Context context;

    @NonNull /*package*/ final ProjectIOProvider provider;

    public ProjectRepository(@NonNull Context context,
                             @NonNull ProjectIOProvider provider) {
        this.context = context;
        this.provider = provider;
    }

    public Observable<Project> getProjects() {

        return Observable.create(new Observable.OnSubscribe<Project>() {
            @Override public void call(Subscriber<? super Project> subscriber) {
                Cursor cursor = null;
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    cursor = contentResolver.query(ChronorgSchema.PROJECTS_URI, null, null, null, null);

                    if (cursor != null && cursor.getCount() > 0) {
                        BaseCursorReader<Project> reader = provider.provideReader(cursor);
                        while (cursor.moveToNext()) {
                            subscriber.onNext(reader.instantiateAndFill());
                        }
                    }
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                } finally {
                    if (cursor != null) cursor.close();
                }

            }
        });
    }

    public Observable<Void> saveProject(@NonNull final Project project) {

        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    BaseContentValuesWriter<Project> writer = provider.provideWriter();

                    ContentValues cv = writer.toContentValues(project);
                    Uri result = contentResolver.insert(ChronorgSchema.PROJECTS_URI, cv);

                    if (result == null) {
                        subscriber.onError(new RuntimeException("Unable to save project !"));
                    } else {
                        subscriber.onCompleted();
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        });
    }
}
