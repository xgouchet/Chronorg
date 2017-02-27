package fr.xgouchet.khronorg.ui.contracts

/**
 * @author Xavier F. Gouchet
 */
interface InstantPickerPresenter {

    fun onReady()

    fun onDatePicked(pickedDate: String)

    fun onTimePicked(pickedTime: String)

    fun onTimezonePicked(pickedTimezone: String)

    fun onCancel()
}

interface InstantPickerView {
    fun pickDate()

    fun pickTime()

    fun pickTimezone()

    fun dismiss()
}