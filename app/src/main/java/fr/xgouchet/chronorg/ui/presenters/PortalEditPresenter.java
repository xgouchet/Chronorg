package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.models.Portal;
import fr.xgouchet.chronorg.data.repositories.PortalRepository;
import fr.xgouchet.chronorg.ui.contracts.presenters.BaseEditPresenter;
import fr.xgouchet.chronorg.ui.contracts.views.BaseEditView;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * @author Xavier Gouchet
 */
@Trace
public class PortalEditPresenter implements BaseEditPresenter<Portal> {

    @Nullable private Portal portal;

    @Nullable /*package*/ BaseEditView<Portal> view;

    @NonNull private final PortalRepository portalRepository;

    public PortalEditPresenter(@NonNull PortalRepository portalRepository) {
        this.portalRepository = portalRepository;
    }

    public void setPortal(@NonNull Portal portal) {
        this.portal = portal;
    }

    @Override public void setView(@NonNull BaseEditView<Portal> view) {
        this.view = view;
        view.setPresenter(this);
    }

    @Override public void subscribe() {
        if (view == null) return;
        if (portal == null) return;

        Portal viewPortal = new Portal();
        viewPortal.setId(portal.getId());
        viewPortal.setProjectId(portal.getProjectId());
        viewPortal.setName(portal.getName());
        viewPortal.setDelay(portal.getDelay().toString());
        viewPortal.setColor(portal.getColor());
        viewPortal.setDirection(portal.getDirection());

        view.setContent(viewPortal);
    }

    @Override public void unsubscribe() {

    }

    @Override public void load(boolean force) {
        // empty
    }

    @Override public void setItemValue(@NonNull Portal viewPortal) {
        if (portal == null) return;

        portal.setName(viewPortal.getName());
        portal.setDelay(viewPortal.getDelay().toString());
        portal.setDirection(viewPortal.getDirection());
        portal.setColor(viewPortal.getColor());
    }

    @Override public void saveItem() {
        if (view == null) return;
        if (portal == null) return;

        portalRepository.save(portal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new EditActionSubscriber(view));
    }

    @Override public void deleteItem() {
        if (view == null) return;
        if (portal == null) return;

        portalRepository.delete(portal)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new EditActionSubscriber(view));
    }
}
