package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.ui.contracts.presenters.BaseListPresenter;
import rx.Observable;

/**
 * @author Xavier Gouchet
 */
@Trace
public class EntityListPresenter extends BaseListPresenter<Entity> {

    @NonNull private final EntityRepository entityRepository;
    @Nullable private Project project;

    public EntityListPresenter(@NonNull EntityRepository entityRepository) {
        this.entityRepository = entityRepository;
    }

    public void setProject(@NonNull Project project) {
        this.project = project;
    }

    @Override protected Observable<Entity> getItemsObservable() {
        return entityRepository.getEntitiesInProject(project.getId());
    }
}
