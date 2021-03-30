package com.nopaingain.bouldereatout.ui.auth

import android.view.View
import androidx.navigation.fragment.findNavController
import com.nopaingain.bouldereatout.R
import com.nopaingain.bouldereatout.ui.base.BaseFragment
import kotlinx.android.synthetic.main.fragment_login.*

class LoginFragment: BaseFragment() {

    override fun getLayoutID(): Int = R.layout.fragment_login

    override fun init() {

    }

    override fun setupUI() {
        btnLogin.setOnClickListener(onClickListener)
        tvForgotPassword.setOnClickListener(onClickListener)
        tvRegister.setOnClickListener(onClickListener)
    }

    override fun onClick(view: View) {
        when(view){
            btnLogin -> {

            }
            tvForgotPassword -> {
                findNavController().navigate(R.id.action_loginFragment_to_forgotPasswordFragment)
            }
            tvRegister -> {
                findNavController().navigate(R.id.action_loginFragment_to_registrationFragment)
            }
        }
    }

    override fun isOnBackPressed(): Boolean = false
}