package fr.xgouchet.chronorg.android.mvvm

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import fr.xgouchet.chronorg.data.room.AppDatabase

abstract class BaseAndroidViewModel(application: Application)
    : AndroidViewModel(application), BaseViewModel {

}
