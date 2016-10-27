package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.repositories.PortalRepository;
import rx.Observable;

/**
 * @author Xavier Gouchet
 */
@Trace
public class PortalListPresenter extends BaseListPresenter<Portal> {

    @NonNull private final PortalRepository eventRepository;
    @Nullable private Project project;

    public PortalListPresenter(@NonNull PortalRepository portalRepository) {
        this.eventRepository = portalRepository;
    }

    public void setProject(@NonNull Project project) {
        this.project = project;
    }

    @Override protected Observable<Portal> getItemsObservable() {
        return eventRepository.getPortalsInProject(project.getId());
    }
}
