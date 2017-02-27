package fr.xgouchet.khronorg.ui.presenters

import fr.xgouchet.khronorg.ui.contracts.InstantPickerView
import io.reactivex.functions.Consumer
import org.joda.time.DateTime
import org.joda.time.ReadableInstant
import kotlin.properties.Delegates

/**
 * @author Xavier F. Gouchet
 */
class InstantPickerPresenter(val consumer: Consumer<ReadableInstant>)
    : fr.xgouchet.khronorg.ui.contracts.InstantPickerPresenter {

    var view: InstantPickerView by Delegates.notNull()

    private var date: String? = null
    private var time: String? = null
    private var timezone: String? = null

    override fun onReady() {
        handleNextStep()
    }

    override fun onDatePicked(pickedDate: String) {
        // TODO ? validate format

        date = pickedDate
        handleNextStep()
    }

    override fun onTimePicked(pickedTime: String) {
        // TODO ? validate format

        time = pickedTime
        handleNextStep()
    }

    override fun onTimezonePicked(pickedTimezone: String) {
        // TODO ? validate format

        timezone = pickedTimezone
        handleNextStep()
    }

    override fun onCancel() {
        view.dismiss()
    }

    private fun handleNextStep() {
        if (date == null) {
            view.pickDate()
        } else if (time == null) {
            view.pickTime()
        } else if (timezone == null) {
            view.pickTimezone()
        } else {
            val dateIso8601 = date + "T" + time + timezone
            val dateTime = DateTime(dateIso8601)

            consumer.accept(dateTime)
        }

    }
}