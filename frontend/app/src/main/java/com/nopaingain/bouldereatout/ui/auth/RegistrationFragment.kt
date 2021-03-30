package com.nopaingain.bouldereatout.ui.auth

import android.view.View
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_registration.*

class RegistrationFragment : BaseFragment() {

    override fun getLayoutID(): Int = R.layout.fragment_registration

    override fun init() {

    }

    override fun setupUI() {
        btnSubmit.setOnClickListener(onClickListener)
    }

    override fun onClick(view: View) {
        when (view) {
            btnSubmit -> {

            }
        }
    }

    override fun isOnBackPressed(): Boolean = true
}