package fr.xgouchet.chronorg.android.activity

import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.widget.DatePicker
import android.widget.TimePicker
import androidx.appcompat.app.AppCompatActivity
import org.joda.time.DateTime
import org.joda.time.Instant
import timber.log.Timber
import java.util.Locale
import java.util.concurrent.TimeUnit

class InstantPickerActivity : AppCompatActivity(),
    DatePickerDialog.OnDateSetListener,
    TimePickerDialog.OnTimeSetListener,
    DialogInterface.OnCancelListener {

    lateinit var defaultValue: DateTime
    var minValue: DateTime? = null
    var maxValue: DateTime? = null

    private var date: String? = null
    private var time: String? = null
    private var timezone: String? = null

    // region Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        defaultValue = if (intent.hasExtra(EXTRA_DEFAULT_VALUE)) {
            Instant(intent.getStringExtra(EXTRA_DEFAULT_VALUE)).toDateTime()
        } else {
            Instant().toDateTime()
        }

        if (intent.hasExtra(EXTRA_MIN_VALUE)) {
            minValue = Instant(intent.getStringExtra(EXTRA_MIN_VALUE)).toDateTime()
        }

        if (intent.hasExtra(EXTRA_MAX_VALUE)) {
            maxValue = Instant(intent.getStringExtra(EXTRA_MAX_VALUE)).toDateTime()
        }
    }

    override fun onResume() {
        super.onResume()
        handleNextStep()
    }

    // endregion

    // region DatePickerDialog.OnDateSetListener

    override fun onDateSet(view: DatePicker?, year: Int, month: Int, dayOfMonth: Int) {
        date = String.format(
            Locale.US,
            "%04d-%02d-%02d",
            year,
            month + 1,
            dayOfMonth
        )
        Timber.i("Set date to $date")
        handleNextStep()
    }

    // endregion

    // region TimePickerDialog.OnTimeSetListener

    override fun onTimeSet(view: TimePicker?, hourOfDay: Int, minute: Int) {
        time = String.format(Locale.US, "%02d:%02d", hourOfDay, minute)
        Timber.i("Set time to $time")
        handleNextStep()
    }

    // endregion

    // region DialogInterface.OnCancelListener

    override fun onCancel(dialog: DialogInterface?) {
        setResult(RESULT_CANCELED)
        finish()
    }

    // region Internal

    private fun handleNextStep() {
        if (date == null) {
            showDatePicker()
        } else if (time == null) {
            showTimePicker()
        } else if (timezone == null) {
            showTimeZonePicker()
        } else {
            val dateIso8601 = date + "T" + time + timezone
            returnResult(Instant(dateIso8601))
        }
    }

    private fun showDatePicker() {
        val year = defaultValue.year
        val month = defaultValue.monthOfYear - 1
        val dayOfMonth = defaultValue.dayOfMonth

        val dialog = DatePickerDialog(this, this, year, month, dayOfMonth)
        val picker: DatePicker = dialog.datePicker
        // Around the birth of JC, because date picker doesn't handle negative
        picker.minDate = minValue?.millis ?: TimeUnit.DAYS.toMillis(-735000)
        // +2739 years (around 4760 AD)
        picker.maxDate = maxValue?.millis ?: TimeUnit.DAYS.toMillis(1000000)
        dialog.setOnCancelListener(this)
        dialog.show()
    }

    private fun showTimePicker() {
        val hourOfDay = defaultValue.hourOfDay
        val minute = defaultValue.minuteOfHour

        val dialog = TimePickerDialog(
            this,
            this,
            hourOfDay,
            minute,
            true
        )

        dialog.setOnCancelListener(this)
        dialog.show()
    }

    private fun showTimeZonePicker() {
        // TODO
        timezone = "Z"
        handleNextStep()
    }

    private fun returnResult(instant: Instant) {
        val result = Intent()
        result.putExtra(EXTRA_RESULT, instant.toString())
        setResult(RESULT_OK, result)
        finish()
    }

    // endregion

    companion object {

        fun createInstantPicker(
            context: Context, defaultValue: Instant?,
            min: Instant? = null,
            max: Instant? = null
        ): Intent {
            val intent = Intent(context, InstantPickerActivity::class.java)
            defaultValue?.let {
                intent.putExtra(EXTRA_DEFAULT_VALUE, it.toString())
            }
            min?.let {
                intent.putExtra(EXTRA_MIN_VALUE, it.toString())
            }
            max?.let {
                intent.putExtra(EXTRA_MAX_VALUE, it.toString())
            }
            return intent
        }

        private const val EXTRA_DEFAULT_VALUE = "default_value"
        private const val EXTRA_MIN_VALUE = "min_value"
        private const val EXTRA_MAX_VALUE = "max_value"

        internal const val EXTRA_RESULT = "result"
    }
}