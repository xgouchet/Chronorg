package fr.xgouchet.chronorg.android.mvvm

import android.content.Intent
import androidx.navigation.NavController
import fr.xgouchet.chronorg.ui.items.Item

interface BaseViewModel {

    fun <T : BaseFragment<*>> setLinkedFragment(linkedFragment: T?)

    suspend fun getData(): List<Item.ViewModel>

    suspend fun onViewEvent(event: Item.Event, navController: NavController)

    fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {}
}
