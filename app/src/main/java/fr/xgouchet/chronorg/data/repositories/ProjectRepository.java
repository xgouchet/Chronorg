package fr.xgouchet.chronorg.data.repositories;

import android.content.ContentResolver;
import android.content.Context;
import android.support.annotation.NonNull;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.ioproviders.ProjectIOProvider;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;

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
            @Override public void call(final Subscriber<? super Project> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    provider.provideQuerier().queryAll(contentResolver, new Action1<Project>() {
                        @Override public void call(Project project) {
                            subscriber.onNext(project);
                        }
                    });
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }

    public Observable<Project> getProject(final int projectId) {
        return Observable.create(new Observable.OnSubscribe<Project>() {
            @Override public void call(final Subscriber<? super Project> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    provider.provideQuerier().query(contentResolver, new Action1<Project>() {
                        @Override public void call(Project project) {
                            subscriber.onNext(project);
                        }
                    }, projectId);
                    subscriber.onCompleted();
                } catch (Exception err) {
                    subscriber.onError(err);
                }
            }
        });
    }

    public Observable<Void> saveProject(@NonNull final Project project) {

        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    boolean success = provider.provideQuerier().save(contentResolver, project);
                    if (success) {
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new RuntimeException("Unable to save project !"));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        });
    }

    public Observable<Void> deleteProject(final @NonNull Project project) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override public void call(Subscriber<? super Void> subscriber) {
                try {
                    ContentResolver contentResolver = context.getContentResolver();
                    boolean success = provider.provideQuerier().delete(contentResolver, project);
                    if (success) {
                        subscriber.onCompleted();
                    } else {
                        subscriber.onError(new RuntimeException("Unable to delete project !"));
                    }
                } catch (Exception e) {
                    subscriber.onError(e);
                }

            }
        });
    }
}
