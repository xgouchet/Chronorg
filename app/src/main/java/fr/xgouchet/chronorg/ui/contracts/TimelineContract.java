package fr.xgouchet.chronorg.ui.contracts;

import android.support.annotation.NonNull;

import fr.xgouchet.chronorg.data.models.Project;
import fr.xgouchet.chronorg.data.models.TimelineShard;
import fr.xgouchet.chronorg.ui.fragments.BaseListView;
import fr.xgouchet.chronorg.ui.presenters.BaseListPresenter;

/**
 * @author Xavier Gouchet
 */
public interface TimelineContract {


    interface View extends BaseListView<Presenter, TimelineShard> {

    }

    interface Presenter extends BaseListPresenter<TimelineShard> {

        void setProject(@NonNull Project project);

        void setView(@NonNull View view);

    }
}
