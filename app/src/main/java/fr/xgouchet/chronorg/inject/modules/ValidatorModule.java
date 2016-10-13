package fr.xgouchet.chronorg.inject.modules;

import dagger.Module;
import dagger.Provides;
import fr.xgouchet.chronorg.inject.annotations.ApplicationScope;
import fr.xgouchet.chronorg.ui.validators.DateTimeInputValidator;
import fr.xgouchet.chronorg.ui.validators.DateTimeRegexValidator;

/**
 * @author Xavier Gouchet
 */
@Module
public class ValidatorModule {

    @Provides
    @ApplicationScope
    public DateTimeInputValidator provideDateTimeInputValidator() {
        return new DateTimeRegexValidator();
    }
}
