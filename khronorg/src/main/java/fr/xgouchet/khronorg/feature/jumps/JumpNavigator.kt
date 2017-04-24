package fr.xgouchet.khronorg.feature.jumps

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.ui.navigators.Navigator

/**
 * @author Xavier F. Gouchet
 */
class JumpNavigator(val activity: Activity) : Navigator<Jump> {
    companion object {
        val EXTRA_JUMP = "jump"
    }

    override fun goToItemDetails(item: Jump) {
        goToItemEdition(item)
    }

    override fun goToItemEdition(item: Jump) {
        val intent = Intent(activity, JumpEditorAktivity::class.java)
        intent.putExtra(EXTRA_JUMP, item)
        activity.startActivity(intent)
    }

    override fun goToItemCreation() {


        AlertDialog.Builder(activity)
                .setTitle(R.string.title_create_jump)
                .setItems(
                        R.array.jumps_creation,
                        {
                            dialog, selection ->
                            when (selection) {
                                0 -> goToJumpCreation()
                                1 -> goToJumpThroughPortalCreation()
                                else -> throw IllegalStateException("Unknown selection for jump creation  : $selection")
                            }
                        }
                )
                .show()


    }

    fun goToJumpCreation() {
        val intent = Intent(activity, JumpEditorAktivity::class.java)
        activity.startActivity(intent)
    }

    fun goToJumpThroughPortalCreation() {
        val intent = Intent(activity, JumpThroughEditorAktivity::class.java)
        activity.startActivity(intent)
    }

    override fun goBack() {
        activity.finish()
    }
}