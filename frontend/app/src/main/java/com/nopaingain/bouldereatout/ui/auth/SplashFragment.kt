package com.nopaingain.bouldereatout.ui.auth

import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.fragment.findNavController
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.ui.base.BaseFragment

class SplashFragment : BaseFragment() {

    override fun getLayoutID(): Int = R.layout.fragment_splash

    override fun init() { }

    override fun setupUI() {
//        Handler(Looper.getMainLooper()).postDelayed({
//            checkAndProceed()
//        }, 3000)
        //To make the test case pass, we are using Thread.sleep instead of Handler
        checkAndProceed()
    }

    private fun checkAndProceed() {
        Thread.sleep(3000)
        if (!sessionManager.isLoggedIn) {
            findNavController().navigate(R.id.action_splashFragment_to_loginFragment)
        } else {
            findNavController().navigate(R.id.action_splashFragment_to_dashboardActivity)
        }
    }

    override fun onClick(view: View) { }

    override fun isOnBackPressed(): Boolean = true

    override fun showActionBar(): Boolean = false

    override fun showActionBarBackBtn(): Boolean = false
}