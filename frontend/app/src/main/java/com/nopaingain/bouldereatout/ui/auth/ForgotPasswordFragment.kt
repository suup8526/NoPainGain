package com.nopaingain.bouldereatout.ui.auth

import android.view.View
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.ui.base.BaseFragment
import com.nopaingain.bouldereatout.utils.isValidEmail
import com.nopaingain.bouldereatout.utils.showAlertDialog
import kotlinx.android.synthetic.main.fragment_forgot_password.*
import kotlinx.android.synthetic.main.fragment_forgot_password.tilEmail
import kotlinx.android.synthetic.main.fragment_forgot_password.tilUsername
import kotlinx.android.synthetic.main.fragment_registration.*

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
                if (validateFields()) {
                    resetPassword()
                }
            }
        }
    }

//    TODO("Not yet implemented")
    private fun resetPassword() {
        context?.showAlertDialog(getString(R.string.feature_coming_soon))
    }


    private fun validateFields() : Boolean {
        return when {
            tilEmail.editText?.text?.toString()?.isEmpty() == true -> {
                tilEmail.error = getString(R.string.email_empty_msg)
                false
            }
            tilUsername.editText?.text?.toString()?.isEmpty() == true -> {
                tilUsername.error = getString(R.string.user_name_empty_msg)
                false
            }
            else -> true
        }
    }

    override fun isOnBackPressed(): Boolean = true

    override fun showActionBar(): Boolean = true

    override fun showActionBarBackBtn(): Boolean = true
}