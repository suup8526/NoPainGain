package com.nopaingain.bouldereatout.ui.dashboard

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.view.Menu
import android.view.MenuItem
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.firebase.analytics.FirebaseAnalytics
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.services.SessionManager
import com.nopaingain.bouldereatout.ui.base.BaseActivity
import com.nopaingain.bouldereatout.ui.base.BaseFragment
import com.nopaingain.bouldereatout.utils.Constants
import com.nopaingain.bouldereatout.utils.showToast

class DashboardActivity : BaseActivity() {

    private lateinit var navController: NavController
    private lateinit var prefs: SharedPreferences

    override fun getContentViewID(): Int = R.layout.activity_dashboard

    override fun setupUI() {
        prefs = SessionManager(this).customPrefs()
        supportActionBar?.title = getString(R.string.app_name)
        navController = findNavController(R.id.dashboardHostFragment)
        setupActionBarWithNavController(navController)
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.dashboardHostFragment)
        val currentFragment = navHostFragment!!.childFragmentManager.fragments[0]
        if (currentFragment is BaseFragment) {
            if (currentFragment.isOnBackPressed()) {
                if (currentFragment is MainListingFragment) {
                    this.finishAffinity()
                } else {
                    super.onBackPressed()
                }
            }
        } else {
            super.onBackPressed()
        }
    }

    @SuppressLint("InvalidAnalyticsName")
    fun onLogoutAction(menuItem: MenuItem) {
        val bundle = bundleOf("username" to "TODO")
        FirebaseAnalytics.getInstance(this).logEvent(Constants.EVENT_LOGOUT, bundle)
        showToast(getString(R.string.logging_out))
        prefs.edit().clear().apply()
        navController.navigate(R.id.authActivity)
        finishAffinity()
    }

    fun onProfileAction(menuItem: MenuItem) {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.dashboardHostFragment)
        val currentFragment = navHostFragment!!.childFragmentManager.fragments[0]
        if (currentFragment is MainListingFragment) {
            findNavController(R.id.dashboardHostFragment).navigate(R.id.action_mainListingFragment_to_profileFragment)
        } else if (currentFragment is RestaurantDetailFragment){
            findNavController(R.id.dashboardHostFragment).navigate(R.id.action_restaurantDetailFragment_to_profileFragment)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.dashboard_menu, menu)
        return true
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}