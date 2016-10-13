package fr.xgouchet.chronorg.inject.components;

import android.content.Context;

import dagger.Component;
import fr.xgouchet.chronorg.data.repositories.EntityRepository;
import fr.xgouchet.chronorg.data.repositories.JumpRepository;
import fr.xgouchet.chronorg.data.repositories.ProjectRepository;
import fr.xgouchet.chronorg.inject.annotations.ApplicationContext;
import fr.xgouchet.chronorg.inject.annotations.ApplicationScope;
import fr.xgouchet.chronorg.inject.modules.GlobalModule;
import fr.xgouchet.chronorg.inject.modules.RepositoryModule;
import fr.xgouchet.chronorg.inject.modules.ValidatorModule;
import fr.xgouchet.chronorg.ui.validators.DateTimeInputValidator;

/**
 * @author Xavier Gouchet
 */
@Component(modules = {GlobalModule.class, RepositoryModule.class, ValidatorModule.class})
@ApplicationScope
public interface ChronorgComponent {

    @ApplicationContext
    Context getApplicationContext();

    ProjectRepository getProjectRepository();

    EntityRepository getEntityRepository();

    JumpRepository getJumpRepository();

    DateTimeInputValidator getDateTimeInputValidator();

}
