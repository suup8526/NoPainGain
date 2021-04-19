package com.nopaingain.bouldereatout.ui.dashboard

import android.view.View
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_profile.*

class ProfileFragment: BaseFragment() {
    override fun getLayoutID(): Int = R.layout.fragment_profile

    override fun init() {

    }

    override fun setupUI() {
        tvProfileName?.text = getString(R.string.prof_name, sessionManager.getName())
        tvProfileEmail?.text = getString(R.string.prof_email, sessionManager.getEmail())
    }

    override fun onClick(view: View) {

    }

    override fun isOnBackPressed(): Boolean = true

    override fun showActionBar(): Boolean = true

    override fun showActionBarBackBtn(): Boolean = true
}