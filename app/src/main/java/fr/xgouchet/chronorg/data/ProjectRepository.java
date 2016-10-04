package fr.xgouchet.chronorg.data;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.model.Project;
import fr.xgouchet.chronorg.provider.dao.ProjectCursorReader;
import fr.xgouchet.chronorg.provider.db.ChronorgSchema;
import rx.Observable;
import rx.Subscriber;

/**
 * @author Xavier Gouchet
 */
@Trace
public class ProjectRepository {

    @NonNull
    private final Context context;

    // TODO ? inject projectReaderFactory ?

    public ProjectRepository(@NonNull Context context) {
        this.context = context;
    }

    public Observable<Project> getProjects() {

        return Observable.create(new Observable.OnSubscribe<Project>() {
            @Override public void call(Subscriber<? super Project> subscriber) {
                Cursor cursor = null;
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    cursor = contentResolver.query(ChronorgSchema.PROJECTS_URI, null, null, null, null);

                    if (cursor != null && cursor.getCount() > 0) {
                        ProjectCursorReader reader = new ProjectCursorReader(cursor);
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
}
