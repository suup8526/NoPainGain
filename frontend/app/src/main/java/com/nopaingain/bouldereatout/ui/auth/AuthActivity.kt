package com.nopaingain.bouldereatout.ui.auth

import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.ui.base.BaseActivity
import com.nopaingain.bouldereatout.ui.base.BaseFragment

class AuthActivity : BaseActivity() {

    override fun getContentViewID(): Int = R.layout.activity_auth

    override fun setupUI() {
        supportActionBar?.title = getString(R.string.app_name)
        val navController = findNavController(R.id.authHostFragment)
        setupActionBarWithNavController(navController)
    }

    override fun onBackPressed() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.authHostFragment)
        val currentFragment = navHostFragment!!.childFragmentManager.fragments[0]
        if (currentFragment is BaseFragment) {
            if (currentFragment.isOnBackPressed()) {
                if (currentFragment is LoginFragment) {
                    this.finishAffinity()
                } else {
                    super.onBackPressed()
                }
            }
        } else {
            super.onBackPressed()
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}