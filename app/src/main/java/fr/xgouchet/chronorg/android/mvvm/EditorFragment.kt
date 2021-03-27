package fr.xgouchet.chronorg.android.mvvm

import androidx.lifecycle.ViewModel
import fr.xgouchet.chronorg.android.activity.DirectionPickerActivity
import fr.xgouchet.chronorg.android.activity.InstantPickerActivity
import fr.xgouchet.chronorg.data.flow.model.Direction
import org.joda.time.Instant
import org.kodein.di.KodeinAware

abstract class EditorFragment<VM> : BaseFragment<VM>(),
    KodeinAware
    where VM : BaseViewModel,
          VM : ViewModel {

    fun promptInstant(requestId: Int, instantValue: Instant?) {
        val intent = InstantPickerActivity.createInstantPicker(
            requireContext(),
            instantValue
        )
        startActivityForResult(intent, requestId)
    }

     fun promptDirection(requestId: Int, direction: Direction) {
         val intent = DirectionPickerActivity.createDirectionPicker(
             requireContext(),
             direction
         )
         startActivityForResult(intent, requestId)
    }
}