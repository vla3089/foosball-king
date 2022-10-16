package com.vladds.foosballking.presentation

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.*
import androidx.navigation.ui.NavigationUI.setupActionBarWithNavController
import com.vladds.foosballking.R
import com.vladds.foosballking.databinding.ActivityFoosballKingBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration

    private val binding by lazy { ActivityFoosballKingBinding.inflate(layoutInflater) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        val host: NavHostFragment = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment) as NavHostFragment? ?: return

        val navController = host.navController
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.playersRankingFragment,
                R.id.gamesHistoryRecordsFragment,
            )
        )

        setupActionBarWithNavController(this, navController, appBarConfiguration)

        binding.toolbar.setNavigationOnClickListener {
            NavigationUI.navigateUp(navController, appBarConfiguration)
        }

        binding.bottomNavigation.setupWithNavController(host.navController)
    }

    override fun onSupportNavigateUp(): Boolean =
        findNavController(R.id.nav_host_fragment).navigateUp(appBarConfiguration)

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        item.onNavDestinationSelected(findNavController(R.id.nav_host_fragment))
                || super.onOptionsItemSelected(item)
}
