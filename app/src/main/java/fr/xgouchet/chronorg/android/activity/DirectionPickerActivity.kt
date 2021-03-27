package fr.xgouchet.chronorg.android.activity

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import fr.xgouchet.chronorg.R
import fr.xgouchet.chronorg.data.flow.model.DIRECTION_FUTURE
import fr.xgouchet.chronorg.data.flow.model.DIRECTION_NONE
import fr.xgouchet.chronorg.data.flow.model.DIRECTION_PAST
import fr.xgouchet.chronorg.data.flow.model.Direction
import fr.xgouchet.chronorg.data.flow.model.name
import org.joda.time.Instant

class DirectionPickerActivity : AppCompatActivity(),
    DialogInterface.OnCancelListener {

    var defaultValue: Direction = -1

    // region Activity

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        defaultValue = intent.getIntExtra(EXTRA_DEFAULT_VALUE, DIRECTION_NONE)
    }

    override fun onResume() {
        super.onResume()
        showDirectionPicker()
    }

    // endregion

    // region DialogInterface.OnCancelListener

    override fun onCancel(dialog: DialogInterface?) {
        setResult(RESULT_CANCELED)
        finish()
    }

    // region Internal

    private fun showDirectionPicker() {
        val choices = arrayOf(DIRECTION_FUTURE, DIRECTION_PAST)
        val labels = choices.map { it.name() }.toTypedArray()
        val checkedItems = choices.map { (it and defaultValue) == it }.toBooleanArray()

        AlertDialog.Builder(this)
            .setTitle(R.string.hint_portal_direction)
            .setIcon(R.drawable.ic_portal)
            .setMultiChoiceItems(labels, checkedItems) { _, which, isChecked ->
                checkedItems[which] = isChecked
            }
            .setPositiveButton(android.R.string.ok) { _, _ ->
                val direction = choices.foldIndexed(0){ i, acc, dir->
                    if (checkedItems[i]) {
                        acc or dir
                    } else {
                        acc
                    }
                }
                returnResult(direction)
            }
            .setOnCancelListener(this)
            .create()
            .show()
    }

    private fun returnResult(direction: Direction) {
        val result = Intent()
        result.putExtra(EXTRA_RESULT, direction)
        setResult(RESULT_OK, result)
        finish()
    }

    // endregion

    companion object {

        fun createDirectionPicker(context: Context, defaultValue: Direction?): Intent {
            val intent = Intent(context, DirectionPickerActivity::class.java)
            defaultValue?.let {
                intent.putExtra(EXTRA_DEFAULT_VALUE, it)
            }
            return intent
        }

        private const val EXTRA_DEFAULT_VALUE = "default_value"

        internal const val EXTRA_RESULT = "result"
    }
}