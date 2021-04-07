package com.nopaingain.bouldereatout.ui.dashboard

import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.ui.base.BaseActivity
import com.nopaingain.bouldereatout.ui.base.BaseFragment

class DashboardActivity : BaseActivity() {
    override fun getContentViewID(): Int = R.layout.activity_dashboard

    override fun setupUI() {
        supportActionBar?.title = getString(R.string.app_name)
        val navController = findNavController(R.id.dashboardHostFragment)
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
}