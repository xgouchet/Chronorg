package fr.xgouchet.chronorg.ui.presenters;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.deezer.android.counsel.annotations.Trace;

import fr.xgouchet.chronorg.data.models.Entity;
import fr.xgouchet.chronorg.data.models.Jump;
import fr.xgouchet.chronorg.data.repositories.JumpRepository;
import fr.xgouchet.chronorg.ui.contracts.presenters.BaseListPresenter;
import rx.Observable;

/**
 * @author Xavier Gouchet
 */
@Trace
public class JumpListPresenter extends BaseListPresenter<Jump> {

    @NonNull private final JumpRepository jumpRepository;
    @Nullable private Entity entity;

    public JumpListPresenter(@NonNull JumpRepository jumpRepository) {
        this.jumpRepository = jumpRepository;
    }

    public void setEntity(@NonNull Entity entity) {
        this.entity = entity;
    }

    @Override protected Observable<Jump> getItemsObservable() {
        return jumpRepository.getJumpsInEntity(entity.getId());
    }
}
