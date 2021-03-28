package fr.xgouchet.chronorg.android.activity

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import fr.xgouchet.chronorg.R

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.debug, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.action_orphan_entities -> findNavController(R.id.nav_host_fragment)
                .navigate(R.id.orphanEntityListFragment)
            R.id.action_orphan_portals -> findNavController(R.id.nav_host_fragment)
                .navigate(R.id.orphanPortalListFragment)
            R.id.action_orphan_events -> findNavController(R.id.nav_host_fragment)
                .navigate(R.id.orphanEventListFragment)
        }
        return super.onOptionsItemSelected(item)
    }
}
