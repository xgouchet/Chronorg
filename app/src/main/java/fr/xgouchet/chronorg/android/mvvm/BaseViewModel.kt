package fr.xgouchet.chronorg.android.mvvm

import androidx.navigation.NavController
import fr.xgouchet.chronorg.ui.items.Item

interface BaseViewModel {

    suspend fun getData(): List<Item.ViewModel>

    suspend fun onViewEvent(event: Item.Event, navController: NavController)

}
