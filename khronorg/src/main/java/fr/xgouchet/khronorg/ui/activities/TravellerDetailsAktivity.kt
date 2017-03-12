package fr.xgouchet.khronorg.ui.activities

import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.RelativeSizeSpan
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.github.salomonbrys.kodein.instance
import fr.xgouchet.khronorg.R
import fr.xgouchet.khronorg.data.formatters.Formatter
import fr.xgouchet.khronorg.data.formatters.ShortInstantFormatter
import fr.xgouchet.khronorg.data.models.Jump
import fr.xgouchet.khronorg.data.models.Traveller
import fr.xgouchet.khronorg.data.repositories.BaseRepository
import fr.xgouchet.khronorg.ui.fragments.JumpListFragment
import fr.xgouchet.khronorg.ui.navigators.JumpNavigator
import fr.xgouchet.khronorg.ui.navigators.TravellerNavigator
import fr.xgouchet.khronorg.ui.navigators.TravellerNavigator.Companion.EXTRA_TRAVELLER
import fr.xgouchet.khronorg.ui.presenters.JumpListPresenter
import org.joda.time.ReadableInstant
import kotlin.properties.Delegates

/**
 * @author Xavier F. Gouchet
 */
class TravellerDetailsAktivity : BaseAktivity() {

    var instantFormatter: Formatter<ReadableInstant> = ShortInstantFormatter
    var traveller: Traveller by Delegates.notNull()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_single_fragment)

        // Get Traveller from intent
        val t = intent.getParcelableExtra<Traveller>(EXTRA_TRAVELLER)
        if (t == null) {
            Toast.makeText(this, R.string.error_traveller_details_empty, Toast.LENGTH_SHORT).show()
            finish()
            return
        }

        traveller = t
        kodein.instance<BaseRepository<Traveller>>().setCurrent(t)
        buildTitle()


        if (savedInstanceState == null) {
            val fragment = JumpListFragment()
            val repository = kodein.instance<BaseRepository<Jump>>()
            val navigator = JumpNavigator(this)
            val presenter = JumpListPresenter(repository, traveller, navigator)

            presenter.view = fragment
            fragment.presenter = presenter

            supportFragmentManager.beginTransaction()
                    .add(R.id.root, fragment)
                    .commit()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.traveller_details, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var result = true

        when (item.itemId) {
            R.id.edit -> {
                TravellerNavigator(this).goToItemEdition(traveller)
            }
            R.id.delete -> {
                // TODO
            }
            else -> result = super.onOptionsItemSelected(item)
        }
        return result
    }

    private fun buildTitle() {
        val builder = SpannableStringBuilder()

        val name = SpannableString(traveller.name)
        builder.append(name)
        builder.append(" ")

        val dates = "(* ${instantFormatter.format(traveller.birth)} - † ${instantFormatter.format(traveller.death)})"
        val datesSpannable = SpannableString(dates)
        datesSpannable.setSpan(RelativeSizeSpan(0.65f), 0, dates.length, 0)
        builder.append(datesSpannable)

        title = builder
    }
}
