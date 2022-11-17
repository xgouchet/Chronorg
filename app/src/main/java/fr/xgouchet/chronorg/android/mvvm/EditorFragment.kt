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

    fun promptInstant(
        requestId: Int,
        instantValue: Instant?,
        min : Instant? = null,
        max : Instant? = null
    ) {
        val intent = InstantPickerActivity.createInstantPicker(
            requireContext(),
            instantValue,
            min,
            max
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