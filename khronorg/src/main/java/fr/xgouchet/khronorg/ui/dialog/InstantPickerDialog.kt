package fr.xgouchet.khronorg.ui.dialog

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import fr.xgouchet.khronorg.commons.time.getLocalTimeZone
import fr.xgouchet.khronorg.ui.contracts.InstantPickerPresenter
import fr.xgouchet.khronorg.ui.contracts.InstantPickerView
import org.joda.time.*
import java.util.*
import java.util.concurrent.TimeUnit.DAYS
import java.util.function.Supplier
import kotlin.properties.Delegates

/**
 * @author Xavier F. Gouchet
 */
class InstantPickerDialog(val context: Context,
                          val defaultInstant: ReadableInstant = Instant.now())
    : InstantPickerView {


    var presenter: InstantPickerPresenter by Delegates.notNull()


    override fun pickDate() {
        val year: Int = defaultInstant.get(DateTimeFieldType.year())
        val month: Int = defaultInstant.get(DateTimeFieldType.monthOfYear()) - 1
        val dayOfMonth: Int = defaultInstant.get(DateTimeFieldType.dayOfMonth())

        val dialog = DatePickerDialog(context,
                {
                    dlg, y, m, d ->
                    val date = String.format(Locale.US, "%04d-%02d-%02d", y, m + 1, d)
                    presenter.onDatePicked(date)
                },
                year,
                month,
                dayOfMonth)

        // setup the picker
        val picker = dialog.datePicker
        // Around the birth of JC, because date picker doesn't handle negative
        picker.minDate = DAYS.toMillis(-718000)
        // +2739 years (around 41000 AD)
        picker.maxDate = DAYS.toMillis(1000000)

        dialog.setOnCancelListener({ presenter.onCancel() })
        dialog.show()
    }

    override fun pickTime() {
        val localInstant = DateTime(defaultInstant.toString(), getLocalTimeZone())
        val hourOfDay: Int = localInstant.get(DateTimeFieldType.hourOfDay())
        val minute: Int = localInstant.get(DateTimeFieldType.minuteOfHour())

        val dialog = TimePickerDialog(context,
                {
                    dlg, h, m ->
                    val time = String.format(Locale.US, "%02d:%02d", h, m)
                    presenter.onTimePicked(time)
                },
                hourOfDay,
                minute,
                true)

        dialog.setOnCancelListener({ presenter.onCancel() })
        dialog.show()
    }

    override fun pickTimezone() {
        // TODO create a timezone picker dialog
        // TODO let the project define a unique timezone
        // "Z"

        presenter.onTimezonePicked(getLocalTimeZone().id)
    }

    override fun dismiss() {
    }

}