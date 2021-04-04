package com.nopaingain.bouldereatout.ui.auth

import android.view.View
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_forgot_password.*

class ForgotPasswordFragment : BaseFragment() {
    override fun getLayoutID(): Int = R.layout.fragment_forgot_password

    override fun init() {

    }

    override fun setupUI() {
        btnReset.setOnClickListener(onClickListener)
    }

    override fun onClick(view: View) {
        when (view) {
            btnReset -> {

            }
        }
    }

    override fun isOnBackPressed(): Boolean = true

    override fun showActionBar(): Boolean = true

    override fun showActionBarBackBtn(): Boolean = true
}